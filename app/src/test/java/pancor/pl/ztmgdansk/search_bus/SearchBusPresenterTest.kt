package pancor.pl.ztmgdansk.search_bus

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subscribers.TestSubscriber
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

    private val ROUTE = Route(10, "10", "Route10")
    private val STOP = BusStop(10, "Stop10")
    private val QUERY = "10"

    private lateinit var presenter: SearchBusPresenter
    private lateinit var testSubscriber: TestSubscriber<Any>
    private lateinit var schedulers: BaseSchedulerProvider
    @Mock
    private lateinit var view: SearchBusContract.View
    @Mock
    private lateinit var dataManager: BusDataContract

    @Before
    fun setupSearchBusPresenter() {
        MockitoAnnotations.initMocks(this)
        testSubscriber = TestSubscriber.create()
        schedulers = TrampolineSchedulerProvider()
        presenter = SearchBusPresenter(dataManager, schedulers)
        presenter.onSetView(view)
    }

    /* TODO
    @Test
    fun writeSearchQueryThenCheckResult() {
        setExpectedDataResultFromDataManager(listOf(ROUTE), listOf(STOP))
        val expectedResult = arrayListOf(
                SearchResultData(Header(R.string.routes), HEADER_VIEW_TYPE),
                SearchResultData(ROUTE, ROUTE_VIEW_TYPE),
                SearchResultData(Header(R.string.bus_stops), HEADER_VIEW_TYPE),
                SearchResultData(STOP, BUS_STOP_VIEW_TYPE))

        presenter.getSearchViewResult(Flowable.just(QUERY))
                .subscribe(testSubscriber)

        testSubscriber.assertNoErrors()
                .assertValues(expectedResult)
    }

    @Test
    fun checkIfSearchWillCallDataSourcesOnlyOneTime() {
        setExpectedDataResultFromDataManager(listOf(ROUTE), listOf(STOP))

        presenter.getSearchViewResult(Flowable.just(QUERY, QUERY))
                .subscribe(testSubscriber)

        testSubscriber.assertValueCount(1)
    }

    @Test
    fun ignoreQueryIfLengthIsLessThanTwo() {
        setExpectedDataResultFromDataManager(listOf(ROUTE), listOf(STOP))

        presenter.getSearchViewResult(Flowable.just("1"))
                .subscribe(testSubscriber)

        testSubscriber.assertNoValues()
    }

    @Test
    fun showLoadingIndicatorWhenUserIsWriting() {
        setExpectedDataResultFromDataManager(listOf(ROUTE), listOf(STOP))

        presenter.getSearchViewResult(Flowable.just(QUERY))
                .subscribe(testSubscriber)

        verify(view).showLoadingIndicator()
    }

    @Test
    fun hideLoadingIndicatorWhenResultFromQueryShowsUp() {
        setExpectedDataResultFromDataManager(listOf(ROUTE), listOf(STOP))

        presenter.getSearchViewResult(Flowable.just(QUERY))
                .subscribe(testSubscriber)

        verify(view).hideLoadingIndicator()
    }

    @Test
    fun whenBusStopsListIsEmptyThenReturnOnlyRoutesList() {
        setExpectedDataResultFromDataManager(listOf(ROUTE), listOf())
        val expectedResult = arrayListOf(
                SearchResultData(Header(R.string.routes), HEADER_VIEW_TYPE),
                SearchResultData(ROUTE, ROUTE_VIEW_TYPE))

        presenter.getSearchViewResult(Flowable.just(QUERY))
                .subscribe(testSubscriber)

        testSubscriber.assertValues(expectedResult)
    }

    @Test
    fun whenRoutesListIsEmptyThenReturnOnlyBusStopsList() {
        setExpectedDataResultFromDataManager(listOf(), listOf(STOP))
        val expectedResult = arrayListOf(
                SearchResultData(Header(R.string.bus_stops), HEADER_VIEW_TYPE),
                SearchResultData(STOP, BUS_STOP_VIEW_TYPE))

        presenter.getSearchViewResult(Flowable.just(QUERY))
                .subscribe(testSubscriber)

        testSubscriber.assertValues(expectedResult)
    }

    @Test
    fun whenSearchResultIsEmptyThenInformViewAboutIt() {
        setExpectedDataResultFromDataManager(listOf(), listOf())

        presenter.getSearchViewResult(Flowable.just(QUERY))
                .subscribe()

        verify(view).emptyResultFromServer()
    }

    private fun setExpectedDataResultFromDataManager(route:List<Route>, stop: List<BusStop>) {
        `when`(dataManager.getBusRoutesByQuery(QUERY)).thenReturn(Flowable.just(route))
        `when`(dataManager.getBusStopsByQuery(QUERY)).thenReturn(Flowable.just(stop))
    }*/
}