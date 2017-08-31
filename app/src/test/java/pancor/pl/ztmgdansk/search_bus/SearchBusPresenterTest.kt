package pancor.pl.ztmgdansk.search_bus

import io.reactivex.Flowable
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import pancor.pl.ztmgdansk.R
import pancor.pl.ztmgdansk.base.BUS_STOP_VIEW_TYPE
import pancor.pl.ztmgdansk.base.HEADER_VIEW_TYPE
import pancor.pl.ztmgdansk.base.ROUTE_VIEW_TYPE
import pancor.pl.ztmgdansk.data.BusDataContract
import pancor.pl.ztmgdansk.data.BusDataManager
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Header
import pancor.pl.ztmgdansk.models.Route
import pancor.pl.ztmgdansk.models.SearchResultData
import pancor.pl.ztmgdansk.tools.schedulers.BaseSchedulerProvider
import pancor.pl.ztmgdansk.tools.schedulers.TrampolineSchedulerProvider

class SearchBusPresenterTest {

    private val ROUTES = listOf(Route(10, "10", "Route10"))
    private val STOPS = listOf(BusStop(10, "Stop10"))
    private val QUERY = "10"

    private lateinit var presenter: SearchBusPresenter
    private lateinit var schedulers: BaseSchedulerProvider
    @Mock
    private lateinit var view: SearchBusContract.View
    @Mock
    private lateinit var dataManager: BusDataContract

    @Before
    fun setupSearchBusPresenter() {
        MockitoAnnotations.initMocks(this)
        schedulers = TrampolineSchedulerProvider()
        presenter = SearchBusPresenter(dataManager, schedulers)
    }

    /*TODO
    @Test
    fun attachePresenterToTheView() {
        verify(view).setPresenter(presenter)
    }

    @Test
    fun writeSearchQueryThenCheckResult() {
        `when`(dataManager.getBusRoutesByQuery(QUERY)).thenReturn(Flowable.just(ROUTES))
        `when`(dataManager.getBusStopsByQuery(QUERY)).thenReturn(Flowable.just(STOPS))

        presenter.setupSearchViewObservable(Observable.just(QUERY))

        verify(view).onSearchResult(ArgumentMatchers.anyList())
    }

    @Test
    fun checkIfSearchWillCallDataSourcesOnlyOneTime() {
        `when`(dataManager.getBusRoutesByQuery(QUERY)).thenReturn(Flowable.just(ROUTES))
        `when`(dataManager.getBusStopsByQuery(QUERY)).thenReturn(Flowable.just(STOPS))

        presenter.setupSearchViewObservable(Observable.just(QUERY, QUERY))

        verify(view, times(1)).onSearchResult(ArgumentMatchers.anyList())
    }

    @Test
    fun ignoreQueryIfLengthIsLessThanTwo() {
        `when`(dataManager.getBusRoutesByQuery(QUERY)).thenReturn(Flowable.just(ROUTES))
        `when`(dataManager.getBusStopsByQuery(QUERY)).thenReturn(Flowable.just(STOPS))

        presenter.setupSearchViewObservable(Observable.just("1"))

        verify(view, never()).onSearchResult(arrayListOf())
    }

    @Test
    fun showLoadingIndicatorWhenUserIsWriting() {
        `when`(dataManager.getBusRoutesByQuery(QUERY)).thenReturn(Flowable.just(ROUTES))
        `when`(dataManager.getBusStopsByQuery(QUERY)).thenReturn(Flowable.just(STOPS))

        presenter.setupSearchViewObservable(Observable.just(QUERY, QUERY))

        verify(view).showLoadingIndicator()
    }

    @Test
    fun hideLoadingIndicatorWhenResultFromQueryShowsUp() {
        `when`(dataManager.getBusRoutesByQuery(QUERY)).thenReturn(Flowable.just(ROUTES))
        `when`(dataManager.getBusStopsByQuery(QUERY)).thenReturn(Flowable.just(STOPS))

        presenter.setupSearchViewObservable(Observable.just(QUERY, QUERY))

        verify(view).hideLoadingIndicator()
    }

    @Test
    fun whenNoResultThenReturnEmptyList() {
        `when`(dataManager.getBusRoutesByQuery(QUERY)).thenReturn(Flowable.just(listOf()))
        `when`(dataManager.getBusStopsByQuery(QUERY)).thenReturn(Flowable.just(listOf()))

        presenter.setupSearchViewObservable(Observable.just(QUERY))

        verify(view).onSearchResult(listOf())
    }

    @Test
    fun whenBusStopsListIsEmptyThenReturnOnlyRoutesList() {
        `when`(dataManager.getBusRoutesByQuery(QUERY)).thenReturn(Flowable.just(ROUTES))
        `when`(dataManager.getBusStopsByQuery(QUERY)).thenReturn(Flowable.just(listOf()))

        presenter.setupSearchViewObservable(Observable.just(QUERY))

        verify(view).onSearchResult(ArgumentMatchers.anyList())
    }

    @Test
    fun whenRoutesListIsEmptyThenReturnOnlyBusStopsList() {
        `when`(dataManager.getBusRoutesByQuery(QUERY)).thenReturn(Flowable.just(listOf()))
        `when`(dataManager.getBusStopsByQuery(QUERY)).thenReturn(Flowable.just(STOPS))

        presenter.setupSearchViewObservable(Observable.just(QUERY))

        verify(view).onSearchResult(ArgumentMatchers.anyList())
    }

    @Test
    fun whenStopsListIsNotEmptyThenAddHeaderToResult() {
        `when`(dataManager.getBusRoutesByQuery(QUERY)).thenReturn(Flowable.just(listOf()))
        `when`(dataManager.getBusStopsByQuery(QUERY)).thenReturn(Flowable.just(STOPS))
        val expectedResult = listOf(SearchResultData(Header(R.string.bus_stops), HEADER_VIEW_TYPE),
                                    SearchResultData(STOPS[0], BUS_STOP_VIEW_TYPE))

        presenter.setupSearchViewObservable(Observable.just(QUERY))

        verify(view).onSearchResult(expectedResult)
    }

    @Test
    fun whenBusRoutesListIsNotEmptyThenAddHeaderToResult() {
        `when`(dataManager.getBusRoutesByQuery(QUERY)).thenReturn(Flowable.just(ROUTES))
        `when`(dataManager.getBusStopsByQuery(QUERY)).thenReturn(Flowable.just(listOf()))
        val expectedResult = listOf(SearchResultData(Header(R.string.routes), HEADER_VIEW_TYPE),
                SearchResultData(ROUTES[0], ROUTE_VIEW_TYPE))

        presenter.setupSearchViewObservable(Observable.just(QUERY))

        verify(view).onSearchResult(expectedResult)
    }*/
}