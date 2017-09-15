package pancor.pl.ztmgdansk.data

import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Result
import pancor.pl.ztmgdansk.models.Route

class BusDataManagerTest {

    val ROUTES = listOf(Route(1, "1", "Route1"), Route(2, "2", "Route2"))
    val STOPS = listOf(BusStop(1, "Stop1"), BusStop(2, "Stop2"))
    val RESPONSE = Result(isError = false, resultCode = Result.OK, stops = STOPS,
            routes = ROUTES)
    val QUERY = "query123"

    @Mock
    private lateinit var localBusDataManager: BusDataContract.Local

    @Mock
    private lateinit var remoteBusDataManager: BusDataContract

    private lateinit var busDataManager: BusDataManager
    private lateinit var testSubscriber: TestSubscriber<Any>

    @Before
    fun setupBusDataManager() {
        MockitoAnnotations.initMocks(this)
        busDataManager = BusDataManager(localBusDataManager, remoteBusDataManager)
        testSubscriber = TestSubscriber()
    }

    @Test
    fun getStopsAndRoutesByQueryThenInsertDataToLocalDatabase() {
        `when`(localBusDataManager.getBusStopsAndRoutesByQuery(QUERY)).thenReturn(Flowable.just(RESPONSE))
        `when`(remoteBusDataManager.getBusStopsAndRoutesByQuery(QUERY)).thenReturn(Flowable.just(RESPONSE))

        busDataManager.getBusStopsAndRoutesByQuery(QUERY)
                .subscribe()

        verify(localBusDataManager).insertBusStops(STOPS)
        verify(localBusDataManager).insertBusRoutes(ROUTES)
    }

    @Test
    fun getStopsAndRoutesByQueryThenDoNotInsertDataToLocalDatabaseWhenIsError() {
        val resultWithError = Result(isError = true, resultCode = Result.UNKNOWN_ERROR,
                stops = listOf(), routes = listOf())
        `when`(localBusDataManager.getBusStopsAndRoutesByQuery(QUERY)).thenReturn(Flowable.just(resultWithError))
        `when`(remoteBusDataManager.getBusStopsAndRoutesByQuery(QUERY)).thenReturn(Flowable.just(resultWithError))

        busDataManager.getBusStopsAndRoutesByQuery(QUERY)
                .subscribe()

        verify(localBusDataManager, times(0)).insertBusStops(STOPS)
        verify(localBusDataManager, times(0)).insertBusRoutes(ROUTES)
    }

    @Test
    fun getRoutesAndStopsByQueryWhenLocalSourceIsNotAvailable() {
        `when`(localBusDataManager.getBusStopsAndRoutesByQuery(QUERY)).thenReturn(Flowable.just(
                Result(isError = false, resultCode = Result.OK, routes = listOf(), stops = listOf())))
        `when`(remoteBusDataManager.getBusStopsAndRoutesByQuery(QUERY)).thenReturn(Flowable.just(RESPONSE))

        busDataManager.getBusStopsAndRoutesByQuery(QUERY)
                .subscribe(testSubscriber)

        testSubscriber.assertValue(RESPONSE)
    }

    @Test
    fun getRoutesAndStopsByQueryWhenNoInternet() {
        `when`(localBusDataManager.getBusStopsAndRoutesByQuery(QUERY)).thenReturn(Flowable.just(RESPONSE))
        `when`(remoteBusDataManager.getBusStopsAndRoutesByQuery(QUERY)).thenReturn(Flowable.just(
                Result(isError = false, resultCode = Result.OK, routes = listOf(), stops = listOf())))

        busDataManager.getBusStopsAndRoutesByQuery(QUERY)
                .subscribe(testSubscriber)

        testSubscriber.assertValue(RESPONSE)
    }

    @Test
    fun getRoutesAndStopsWithoutRepetition() {
        `when`(localBusDataManager.getBusStopsAndRoutesByQuery(QUERY)).thenReturn(Flowable.just(RESPONSE))
        `when`(remoteBusDataManager.getBusStopsAndRoutesByQuery(QUERY)).thenReturn(Flowable.just(RESPONSE))

        busDataManager.getBusStopsAndRoutesByQuery(QUERY)
                .subscribe(testSubscriber)

        testSubscriber.assertValue(RESPONSE)
    }

    @Test
    fun getRoutesAndStopsThenCombineDataFromTwoSourcesIntoOne() {
        val route = Route(23, "23", "Route23")
        val stop = BusStop(23, "Stop23")
        val remoteResult = Result(isError = false, resultCode = Result.OK, routes = listOf(route),
                stops = listOf(stop))
        `when`(localBusDataManager.getBusStopsAndRoutesByQuery(QUERY)).thenReturn(Flowable.just(RESPONSE))
        `when`(remoteBusDataManager.getBusStopsAndRoutesByQuery(QUERY)).thenReturn(Flowable.just(remoteResult))
        val expectedResult = Result(isError = false, resultCode = Result.OK, routes = ROUTES.plus(route),
                stops = STOPS.plus(stop))

        busDataManager.getBusStopsAndRoutesByQuery(QUERY)
                .subscribe(testSubscriber)

        testSubscriber.assertValue(expectedResult)
    }
}