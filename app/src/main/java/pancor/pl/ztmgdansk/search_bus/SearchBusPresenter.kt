package pancor.pl.ztmgdansk.search_bus

import io.reactivex.Flowable
import pancor.pl.ztmgdansk.di.ActivityScope
import pancor.pl.ztmgdansk.base.BUS_STOP_VIEW_TYPE
import pancor.pl.ztmgdansk.base.HEADER_VIEW_TYPE
import pancor.pl.ztmgdansk.base.ROUTE_VIEW_TYPE
import pancor.pl.ztmgdansk.data.BusDataContract
import pancor.pl.ztmgdansk.models.*
import pancor.pl.ztmgdansk.tools.schedulers.BaseSchedulerProvider
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ActivityScope
class SearchBusPresenter @Inject constructor(private val busDataManager: BusDataContract,
                                             private val schedulers: BaseSchedulerProvider): SearchBusContract.Presenter {

    private lateinit var view: SearchBusContract.View

    override fun getSearchViewResult(searchViewObservable: Flowable<String>): Flowable<ArrayList<SearchResultData>> {
        return searchViewObservable
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter { query -> query.length > 1 }
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .doOnNext { view.showLoadingIndicator() }
                .observeOn(schedulers.io())
                .flatMap { busDataManager.getBusStopsAndRoutesByQuery(it) }
                .map { (isError, resultCode, routes, stops) ->
                    if (isError) {
                        resolveError(resultCode)
                    }
                    mergeRoutesWithStopsAndAddHeaders(routes, stops)
                }
                .observeOn(schedulers.ui())
                .filter { returnTrueWhenListIsNotEmpty(it)}
                .doOnNext{ view.hideLoadingIndicator() }
    }

    private fun mergeRoutesWithStopsAndAddHeaders(routes: List<Route>, stops: List<BusStop>):
            ArrayList<SearchResultData> {
        val searchResultData = arrayListOf<SearchResultData>()
        if (routes.isNotEmpty()) {
            searchResultData.add(SearchResultData(Header(Header.ROUTE), HEADER_VIEW_TYPE))
            routes.mapTo(searchResultData) { SearchResultData(it, ROUTE_VIEW_TYPE) }
        }
        if (stops.isNotEmpty()) {
            searchResultData.add(SearchResultData(Header(Header.BUS_STOP), HEADER_VIEW_TYPE))
            stops.mapTo(searchResultData) { SearchResultData(it, BUS_STOP_VIEW_TYPE) }
        }
        return searchResultData
    }

    private fun resolveError(errorCode: Int) {
        when (errorCode) {
            Result.UNKNOWN_ERROR -> view.onUnknownErrorOnServerSide()
            Result.NO_INTERNET_CONNECTION -> view.onNoInternetConnection()
            else -> throw IllegalArgumentException("Could not resolve errorCode: $errorCode")
        }
    }

    private fun returnTrueWhenListIsNotEmpty(list: ArrayList<SearchResultData>): Boolean {
        if (list.isEmpty()) {
            view.onEmptyResultFromServer()
            return false
        } else {
            return true
        }
    }

    override fun onSetView(view: SearchBusContract.View) {
         this.view = view
    }
}