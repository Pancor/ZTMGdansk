package pancor.pl.ztmgdansk.search_bus

import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import pancor.pl.ztmgdansk.R
import pancor.pl.ztmgdansk.base.BUS_STOP_VIEW_TYPE
import pancor.pl.ztmgdansk.base.HEADER_VIEW_TYPE
import pancor.pl.ztmgdansk.base.ROUTE_VIEW_TYPE
import pancor.pl.ztmgdansk.data.BusDataContract
import pancor.pl.ztmgdansk.models.*
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

        verify(view).onEmptyResultFromServer()
    }

    @Test
    fun whenRoutesListIsNotEmptyThenAddHeader() {
        setExpectedDataResultFromDataManager(listOf(ROUTE), listOf())
        val expectedResult = arrayListOf(SearchResultData(Header(R.string.routes), HEADER_VIEW_TYPE),
                SearchResultData(ROUTE, ROUTE_VIEW_TYPE))

        presenter.getSearchViewResult(Flowable.just(QUERY))
                .subscribe(testSubscriber)

        testSubscriber.assertValue(expectedResult)
    }

    @Test
    fun whenBusStopListIsNotEmptyThenAddHeader() {
        setExpectedDataResultFromDataManager(listOf(), listOf(STOP))
        val expectedResult = arrayListOf(SearchResultData(Header(R.string.bus_stops), HEADER_VIEW_TYPE),
                SearchResultData(STOP, BUS_STOP_VIEW_TYPE))

        presenter.getSearchViewResult(Flowable.just(QUERY))
                .subscribe(testSubscriber)

        testSubscriber.assertValue(expectedResult)
    }

    @Test
    fun whenNoInternetThenInformViewAboutIt() {
        setExpectedDataResultFromDataManager(listOf(), listOf(), Result.NO_INTERNET_CONNECTION)

        presenter.getSearchViewResult(Flowable.just(QUERY))
                .subscribe()

        verify(view).onNoInternetConnection()
    }

    @Test
    fun whenUnknownErrorOnServerSideOccurredThenInformViewAboutIt() {
        setExpectedDataResultFromDataManager(listOf(), listOf(), Result.UNKNOWN_ERROR)

        presenter.getSearchViewResult(Flowable.just(QUERY))
                .subscribe()

        verify(view).onUnknownErrorOnServerSide()
    }

    @Test
    fun whenNoInternetThenStillShowDataFromLocalSource() {
        setExpectedDataResultFromDataManager(listOf(ROUTE), listOf(), Result.NO_INTERNET_CONNECTION)
        val expectedResult = arrayListOf(
                SearchResultData(Header(R.string.routes), HEADER_VIEW_TYPE),
                SearchResultData(ROUTE, ROUTE_VIEW_TYPE))

        presenter.getSearchViewResult(Flowable.just(QUERY))
                .subscribe(testSubscriber)

        testSubscriber.assertValue(expectedResult)
    }

    @Test
    fun whenUnknownErrorOnServerSideOccurredThenStillShowDataFromLocalSource() {
        setExpectedDataResultFromDataManager(listOf(ROUTE), listOf(), Result.UNKNOWN_ERROR)
        val expectedResult = arrayListOf(
                SearchResultData(Header(R.string.routes), HEADER_VIEW_TYPE),
                SearchResultData(ROUTE, ROUTE_VIEW_TYPE))

        presenter.getSearchViewResult(Flowable.just(QUERY))
                .subscribe(testSubscriber)

        testSubscriber.assertValue(expectedResult)
    }

    @Test
    fun whenUnknownResultCodeFromResultThenThrowException() {
        val WRONG_RESULT_CODE = 805720485
        setExpectedDataResultFromDataManager(listOf(), listOf(), WRONG_RESULT_CODE)

        presenter.getSearchViewResult(Flowable.just(QUERY))
                .subscribe(testSubscriber)

        testSubscriber.assertError(IllegalArgumentException::class.java)
                .assertErrorMessage("Could not resolve errorCode: $WRONG_RESULT_CODE")
    }

    private fun setExpectedDataResultFromDataManager(route: List<Route>, stop: List<BusStop>) {
        val result = Result(isError = false, resultCode = Result.OK, routes = route, stops = stop)
        `when`(dataManager.getBusStopsAndRoutesByQuery(QUERY)).thenReturn(Flowable.just(result))
    }

    private fun setExpectedDataResultFromDataManager(route: List<Route>, stop: List<BusStop>, errorCode: Int) {
        val result = Result(isError = true, resultCode = errorCode, routes = route, stops = stop)
        `when`(dataManager.getBusStopsAndRoutesByQuery(QUERY)).thenReturn(Flowable.just(result))
    }
}