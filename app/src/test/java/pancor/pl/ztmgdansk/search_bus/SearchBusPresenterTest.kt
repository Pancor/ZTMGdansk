package pancor.pl.ztmgdansk.search_bus

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import pancor.pl.ztmgdansk.data.BusDataManager
import java.util.concurrent.TimeUnit
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Route

class SearchBusPresenterTest {

    private val ROUTES = listOf(Route(10, "10", "Route10"))
    private val STOPS = listOf(BusStop(20, "Stop20"))
    private val QUERY = "query"

    private lateinit var testSubscriber: TestSubscriber<Any>
    private lateinit var presenter: SearchBusPresenter
    @Mock
    private lateinit var view: SearchBusContract.View
    @Mock
    private lateinit var dataManager: BusDataManager

    @Before
    fun setupSearchBusPresenter() {
        MockitoAnnotations.initMocks(this)
        testSubscriber = TestSubscriber()
        presenter = SearchBusPresenter(view, dataManager)
    }

    @Test
    fun attachePresenterToTheView() {
        presenter = SearchBusPresenter(view, dataManager)

        verify(view).setPresenter(presenter)
    }

    @Test
    fun writeSearchQueryThenCheckResult() {
        `when`(dataManager.getBusRoutesByQuery(QUERY)).thenReturn(Flowable.just(ROUTES))
        `when`(dataManager.getBusStopsByQuery(QUERY)).thenReturn(Flowable.just(STOPS))

        presenter.setupSearchViewObservable(Observable.just("10"))

        verify(view).onSearchResult(listOf(), listOf())
    }

    /*@Test
    fun checkIfSearchStartsAfter500msOfWait() {
        val emmitQuery = Observable.zip(
                Observable.interval(400, TimeUnit.MILLISECONDS),
                Observable.just("10", "100"),
                BiFunction<Long, String, String> { t1, t2 -> t2 }
        )
        `when`(dataManager.getBusRoutesByQuery(QUERY)).thenReturn(Flowable.empty())
        `when`(dataManager.getBusStopsByQuery(QUERY)).thenReturn(Flowable.empty())

        presenter.setupSearchViewObservable(emmitQuery)

        verify(view, times(1)).onSearchResult(listOf(), listOf())
    }*/
}