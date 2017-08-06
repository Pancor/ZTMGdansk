package pancor.pl.ztmgdansk.search_bus

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import pancor.pl.ztmgdansk.base.ActivityScope
import pancor.pl.ztmgdansk.data.BusDataContract
import pancor.pl.ztmgdansk.data.BusDataManager
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Route
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ActivityScope
class SearchBusPresenter @Inject constructor(private val view: SearchBusContract.View,
                                             private val busDataManager: BusDataContract): SearchBusContract.Presenter {

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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { handleResultFromSearch(it) }
    }

    private fun handleResultFromSearch(query: String) {
        disposable.add(Flowable.zip(busDataManager.getBusRoutesByQuery(query),
                     busDataManager.getBusStopsByQuery(query),
                     BiFunction<List<Route>, List<BusStop>, Unit> {
                         routes, stops -> view.onSearchResult(routes, stops) })
                     .subscribeOn(Schedulers.io())
                     .observeOn(AndroidSchedulers.mainThread())
                     .subscribe())
    }
}