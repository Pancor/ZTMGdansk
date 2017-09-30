package pancor.pl.ztmgdansk.data

import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Result
import pancor.pl.ztmgdansk.models.Route

class BusDataManagerTest {

    val ROUTES = listOf(Route(1, "1", "Route1"), Route(2, "2", "Route2"))
    val STOPS = listOf(BusStop(1, "Stop1"), BusStop(2, "Stop2"))
    val RESULT = Result(isError = false, resultCode = Result.OK, stops = STOPS, routes = ROUTES)
    val EMPTY_RESULT = Result(isError = false, resultCode = Result.OK)
    val QUERY = "query123"

    @Mock
    private lateinit var localBusDataManager: BusDataContract.Local
    @Mock
    private lateinit var remoteBusDataManager: BusDataContract
    @Mock
    private lateinit var cacheBusDataManager: BusDataContract.Cache
        private lateinit var busDataManager: BusDataManager
        private lateinit var testSubscriber: TestSubscriber<Any>

        @Before
        fun setupBusDataManager() {
            MockitoAnnotations.initMocks(this)
            busDataManager = BusDataManager(localBusDataManager, cacheBusDataManager, remoteBusDataManager)
        testSubscriber = TestSubscriber()
    }

    @Test
    fun getStopsAndRoutesByQueryThenInsertDataToLocalDatabase() {
        setExpectedResultFromLocalAndRemoteSource(EMPTY_RESULT, RESULT)
        setupEmptyCache()

        busDataManager.getBusStopsAndRoutesByQuery(QUERY)
                .subscribe()

        verify(localBusDataManager).insertBusStops(STOPS)
        verify(localBusDataManager).insertBusRoutes(ROUTES)
    }

    @Test
    fun getStopsAndRoutesByQueryThenDoNotInsertDataToLocalDatabaseWhenIsError() {
        val resultWithError = Result(isError = true, resultCode = Result.UNKNOWN_ERROR)
        setExpectedResultFromLocalAndRemoteSource(resultWithError, resultWithError)
        setupEmptyCache()

        busDataManager.getBusStopsAndRoutesByQuery(QUERY)
                .subscribe()

        verify(localBusDataManager, times(0)).insertBusStops(STOPS)
        verify(localBusDataManager, times(0)).insertBusRoutes(ROUTES)
    }

    @Test
    fun getRoutesAndStopsByQueryWhenLocalSourceIsNotAvailable() {
        setExpectedResultFromLocalAndRemoteSource(EMPTY_RESULT, RESULT)
        setupEmptyCache()

        busDataManager.getBusStopsAndRoutesByQuery(QUERY)
                .subscribe(testSubscriber)

        testSubscriber.assertValue(RESULT)
    }

    @Test
    fun getRoutesAndStopsByQueryWhenNoInternet() {
        val noInternetResult = Result(isError = true, resultCode = Result.NO_INTERNET_CONNECTION)
        setExpectedResultFromLocalAndRemoteSource(RESULT, noInternetResult)
        setupEmptyCache()
        val expectedResult = Result(isError = true, resultCode = Result.NO_INTERNET_CONNECTION,
                routes = ROUTES, stops = STOPS)

        busDataManager.getBusStopsAndRoutesByQuery(QUERY)
                .subscribe(testSubscriber)

        testSubscriber.assertValue(expectedResult)
    }

    @Test
    fun getRoutesAndStopsWithoutRepetition() {
        setExpectedResultFromLocalAndRemoteSource(RESULT, RESULT)
        setupEmptyCache()

        busDataManager.getBusStopsAndRoutesByQuery(QUERY)
                .subscribe(testSubscriber)

        testSubscriber.assertValue(RESULT)
    }

    @Test
    fun getRoutesAndStopsThenCombineDataFromTwoSourcesIntoOne() {
        val route = Route(23, "23", "Route23")
        val stop = BusStop(23, "Stop23")
        val remoteResult = Result(isError = false, resultCode = Result.OK, routes = listOf(route),
                stops = listOf(stop))
        setExpectedResultFromLocalAndRemoteSource(RESULT, remoteResult)
        setupEmptyCache()
        val expectedResult = Result(isError = false, resultCode = Result.OK, routes = ROUTES.plus(route),
                stops = STOPS.plus(stop))

        busDataManager.getBusStopsAndRoutesByQuery(QUERY)
                .subscribe(testSubscriber)

        testSubscriber.assertValue(expectedResult)
    }

    @Test
    fun whenCacheDataIsEmptyAndNetworkResultIsOkThenInsertResultToCache() {
        setExpectedResultFromLocalAndRemoteSource(RESULT, RESULT)
        setupEmptyCache()

        busDataManager.getBusStopsAndRoutesByQuery(QUERY)
                .subscribe()

        verify(cacheBusDataManager).insertResultToCache(any(), any())
    }

    @Test
    fun whenCacheIsNotEmptyThenReturnResultFromIt() {
        `when`(cacheBusDataManager.getBusStopsAndRoutesByQuery(QUERY)).thenReturn(Flowable.just(RESULT))
        setExpectedResultFromLocalAndRemoteSource(EMPTY_RESULT, EMPTY_RESULT)

        busDataManager.getBusStopsAndRoutesByQuery(QUERY)
                .subscribe(testSubscriber)

        testSubscriber.assertValue(RESULT)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }

    private fun setExpectedResultFromLocalAndRemoteSource(local: Result, remote: Result) {
        `when`(localBusDataManager.getBusStopsAndRoutesByQuery(QUERY)).thenReturn(Flowable.just(local))
        `when`(remoteBusDataManager.getBusStopsAndRoutesByQuery(QUERY)).thenReturn(Flowable.just(remote))
    }

    private fun setupEmptyCache() {
        val cacheResult = Result(isError = true, resultCode = Result.NOT_IN_CACHE)
        `when`(cacheBusDataManager.getBusStopsAndRoutesByQuery(QUERY)).thenReturn(Flowable.just(cacheResult))
    }
}