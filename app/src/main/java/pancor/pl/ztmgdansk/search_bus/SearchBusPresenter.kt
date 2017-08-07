package pancor.pl.ztmgdansk.search_bus

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import pancor.pl.ztmgdansk.base.ActivityScope
import pancor.pl.ztmgdansk.data.BusDataContract
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Route
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
        disposable.add(Flowable.zip(busDataManager.getBusRoutesByQuery(query),
                     busDataManager.getBusStopsByQuery(query),
                     BiFunction<List<Route>, List<BusStop>, Pair<List<Route>, List<BusStop>>> {
                         routes, stops ->  Pair(routes, stops)})
                     .subscribeOn(schedulers.io())
                     .observeOn(schedulers.ui())
                     .subscribe { (first, second) -> view.onSearchResult(first, second)
                                                     view.hideLoadingIndicator()})
    }
}