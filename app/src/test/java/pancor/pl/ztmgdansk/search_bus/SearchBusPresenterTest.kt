package pancor.pl.ztmgdansk.search_bus

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subscribers.TestSubscriber
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit
import pancor.pl.ztmgdansk.data.BusDataContract
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Route
import pancor.pl.ztmgdansk.tools.schedulers.BaseSchedulerProvider
import pancor.pl.ztmgdansk.tools.schedulers.TrampolineSchedulerProvider

class SearchBusPresenterTest {

    private val ROUTES = listOf(Route(10, "10", "Route10"))
    private val STOPS = listOf(BusStop(10, "Stop10"))
    private val QUERY = "10"

    private lateinit var testSubscriber: TestSubscriber<Any>
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
        testSubscriber = TestSubscriber()
        presenter = SearchBusPresenter(view, dataManager, schedulers)
    }

    @After
    fun cleanUp() {
        presenter.onStop()
    }

    @Test
    fun attachePresenterToTheView() {
        verify(view).setPresenter(presenter)
    }

    @Test
    fun writeSearchQueryThenCheckResult() {
        `when`(dataManager.getBusRoutesByQuery(QUERY)).thenReturn(Flowable.just(ROUTES))
        `when`(dataManager.getBusStopsByQuery(QUERY)).thenReturn(Flowable.just(STOPS))

        presenter.setupSearchViewObservable(Observable.just(QUERY))

        verify(view).onSearchResult(ROUTES, STOPS)
    }

    @Test
    fun checkIfSearchWillCallDataSourcesOnlyOneTime() {
        `when`(dataManager.getBusRoutesByQuery(QUERY)).thenReturn(Flowable.just(ROUTES))
        `when`(dataManager.getBusStopsByQuery(QUERY)).thenReturn(Flowable.just(STOPS))

        presenter.setupSearchViewObservable(Observable.just(QUERY, QUERY))

        verify(view, times(1)).onSearchResult(ROUTES, STOPS)
    }

    @Test
    fun ignoreQueryIfLengthIsLessThanTwo() {
        `when`(dataManager.getBusRoutesByQuery(QUERY)).thenReturn(Flowable.just(ROUTES))
        `when`(dataManager.getBusStopsByQuery(QUERY)).thenReturn(Flowable.just(STOPS))

        presenter.setupSearchViewObservable(Observable.just("1"))

        verify(view, never()).onSearchResult(ROUTES, STOPS)
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
}