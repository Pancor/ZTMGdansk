package pancor.pl.ztmgdansk.search_bus

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import pancor.pl.ztmgdansk.R
import pancor.pl.ztmgdansk.base.ActivityScope
import pancor.pl.ztmgdansk.base.BUS_STOP_VIEW_TYPE
import pancor.pl.ztmgdansk.base.HEADER_VIEW_TYPE
import pancor.pl.ztmgdansk.base.ROUTE_VIEW_TYPE
import pancor.pl.ztmgdansk.data.BusDataContract
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Header
import pancor.pl.ztmgdansk.models.Route
import pancor.pl.ztmgdansk.models.SearchResultData
import pancor.pl.ztmgdansk.tools.schedulers.BaseSchedulerProvider
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ActivityScope
class SearchBusPresenter @Inject constructor(private val view: SearchBusContract.View,
                                             private val busDataManager: BusDataContract,
                                             private val schedulers: BaseSchedulerProvider): SearchBusContract.Presenter {

    private val disposable = CompositeDisposable()

    init {
        view.setPresenter(this)
    }

    override fun onStop() {
        disposable.dispose()
    }

    override fun setupSearchViewObservable(observable: Observable<String>) {
        observable
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter { query -> query.length > 1 }
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe { view.showLoadingIndicator()
                             handleResultFromSearch(it) }
    }

    private fun handleResultFromSearch(query: String) {
        Flowable.zip(busDataManager.getBusRoutesByQuery(query),
                     busDataManager.getBusStopsByQuery(query),
                     BiFunction<List<Route>, List<BusStop>, Pair<List<Route>, List<BusStop>>> {
                         routes, stops ->  Pair(routes, stops)})
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.io())
                .subscribe { (routes, stops) -> mergeRoutesWithStopsAndAddHeaders(routes, stops) }
    }

    private fun mergeRoutesWithStopsAndAddHeaders(routes: List<Route>, stops: List<BusStop>) {
        val routesFlowable = Flowable.just(routes)
                .filter { list -> list.isNotEmpty() }
                .flatMapIterable { list -> list }
                .map { route -> SearchResultData(route, ROUTE_VIEW_TYPE) }
                .startWith(SearchResultData(Header(R.string.routes), HEADER_VIEW_TYPE))
                .toList()

        val stopsFlowable = Flowable.just(stops)
                .filter { list -> list.isNotEmpty() }
                .flatMapIterable { list -> list }
                .map { stop -> SearchResultData(stop, BUS_STOP_VIEW_TYPE) }
                .startWith(SearchResultData(Header(R.string.bus_stops), HEADER_VIEW_TYPE))
                .toList()

        routesFlowable.concatWith(stopsFlowable)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe { searchDataResult -> view.hideLoadingIndicator()
                                                 view.onSearchResult(searchDataResult)}
    }
}