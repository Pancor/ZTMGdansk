package pancor.pl.ztmgdansk.data

import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import pancor.pl.ztmgdansk.data.local.LocalBusDataContract

import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Route

class BusDataManagerTest {

    val ROUTES = listOf(Route(1, "1", "Route1"), Route(2, "2", "Route2"))
    val STOPS = listOf(BusStop(1, "Stop1"), BusStop(2, "Stop2"))
    val QUERY = "query123"

    @Mock
    private lateinit var localBusDataManager: LocalBusDataContract

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
    fun getRoutesFromRemoteSourceWhenLocalSourceIsNotAvailable() {
        `when`(localBusDataManager.getBusRoutes()).thenReturn(Flowable.empty())
        `when`(remoteBusDataManager.getBusRoutes()).thenReturn(Flowable.just(ROUTES))

        busDataManager.getBusRoutes().subscribe(testSubscriber)

        verify(remoteBusDataManager).getBusRoutes()
        testSubscriber.assertValue(ROUTES)
    }

    @Test
    fun getRoutesFromLocalSourceWhenRemoteSourceIsNotAvailable(){
        `when`(localBusDataManager.getBusRoutes()).thenReturn(Flowable.just(ROUTES))
        `when`(remoteBusDataManager.getBusRoutes()).thenReturn(Flowable.empty())

        busDataManager.getBusRoutes().subscribe(testSubscriber)

        verify(localBusDataManager).getBusRoutes()
        testSubscriber.assertValue(ROUTES)
    }

    @Test
    fun getStopsFromRemoteSourceWhenLocalSourceIsNotAvailable() {
        `when`(localBusDataManager.getBusStops()).thenReturn(Flowable.empty())
        `when`(remoteBusDataManager.getBusStops()).thenReturn(Flowable.just(STOPS))

        busDataManager.getBusStops().subscribe(testSubscriber)

        verify(remoteBusDataManager).getBusStops()
        testSubscriber.assertValue(STOPS)
    }

    @Test
    fun getStopsFromLocalSourceWhenRemoteSourceIsNotAvailable() {
        `when`(localBusDataManager.getBusStops()).thenReturn(Flowable.just(STOPS))
        `when`(remoteBusDataManager.getBusStops()).thenReturn(Flowable.empty())

        busDataManager.getBusStops().subscribe(testSubscriber)

        verify(localBusDataManager).getBusStops()
        testSubscriber.assertValue(STOPS)
    }

    @Test
    fun getStopsFromRemoteThenCheckIfStopsAreInsertedToLocalDatabase() {
        `when`(localBusDataManager.getBusStops()).thenReturn(Flowable.empty())
        `when`(remoteBusDataManager.getBusStops()).thenReturn(Flowable.just(STOPS))

        busDataManager.getBusStops().subscribe()

        verify(localBusDataManager).insertBusStops(STOPS)
    }

    @Test
    fun getRoutesFromRemoteThenCheckIfRoutesAreInsertedToLocalDatabase() {
        `when`(localBusDataManager.getBusRoutes()).thenReturn(Flowable.empty())
        `when`(remoteBusDataManager.getBusRoutes()).thenReturn(Flowable.just(ROUTES))

        busDataManager.getBusRoutes().subscribe()

        verify(localBusDataManager).insertBusRoutes(ROUTES)
    }

    @Test
    fun getStopsThenCheckIfOnlyOneTimeStopsAreEmitted() {
        `when`(localBusDataManager.getBusStops()).thenReturn(Flowable.just(STOPS))
        `when`(remoteBusDataManager.getBusStops()).thenReturn(Flowable.just(STOPS))

        busDataManager.getBusStops().subscribe(testSubscriber)

        testSubscriber.assertValueCount(1)
    }

    @Test
    fun getRoutesThenCheckIfOnlyOneTimeRoutesAreEmitted() {
        `when`(localBusDataManager.getBusRoutes()).thenReturn(Flowable.just(ROUTES))
        `when`(remoteBusDataManager.getBusRoutes()).thenReturn(Flowable.just(ROUTES))

        busDataManager.getBusRoutes().subscribe(testSubscriber)

        testSubscriber.assertValueCount(1)
    }

    @Test
    fun getStopsByQueryThenCheckIfStopsAreInsertedToLocalDatabase() {
        `when`(localBusDataManager.getBusStopsByQuery(QUERY)).thenReturn(Flowable.just(STOPS))
        `when`(remoteBusDataManager.getBusStopsByQuery(QUERY)).thenReturn(Flowable.just(STOPS))

        busDataManager.getBusStopsByQuery(QUERY).subscribe()

        verify(localBusDataManager).insertBusStops(STOPS)
    }

    @Test
    fun getRoutesByQueryThenCheckIfRoutesAreInsertedToLocalDatabase() {
        `when`(localBusDataManager.getBusRoutesByQuery(QUERY)).thenReturn(Flowable.just(ROUTES))
        `when`(remoteBusDataManager.getBusRoutesByQuery(QUERY)).thenReturn(Flowable.just(ROUTES))

        busDataManager.getBusRoutesByQuery(QUERY).subscribe()

        verify(localBusDataManager).insertBusRoutes(ROUTES)
    }

    @Test
    fun getRoutesByQueryWhenLocalSourceIsNotAvailable() {
        `when`(localBusDataManager.getBusRoutesByQuery(QUERY)).thenReturn(Flowable.empty())
        `when`(remoteBusDataManager.getBusRoutesByQuery(QUERY)).thenReturn(Flowable.just(ROUTES))

        busDataManager.getBusRoutesByQuery(QUERY).subscribe(testSubscriber)

        testSubscriber.assertValue(ROUTES)
    }

    @Test
    fun getStopsByQueryWhenLocalSourceIsNotAvailable() {
        `when`(localBusDataManager.getBusStopsByQuery(QUERY)).thenReturn(Flowable.empty())
        `when`(remoteBusDataManager.getBusStopsByQuery(QUERY)).thenReturn(Flowable.just(STOPS))

        busDataManager.getBusStopsByQuery(QUERY).subscribe(testSubscriber)

        testSubscriber.assertValue(STOPS)
    }

    @Test
    fun getRoutesByQueryWhenRemoteSourceIsNotAvailable() {
        `when`(localBusDataManager.getBusRoutesByQuery(QUERY)).thenReturn(Flowable.just(ROUTES))
        `when`(remoteBusDataManager.getBusRoutesByQuery(QUERY)).thenReturn(Flowable.empty())

        busDataManager.getBusRoutesByQuery(QUERY).subscribe(testSubscriber)

        testSubscriber.assertValue(ROUTES)
    }

    @Test
    fun getStopsByQueryWhenRemoteSourceIsNotAvailable() {
        `when`(localBusDataManager.getBusStopsByQuery(QUERY)).thenReturn(Flowable.just(STOPS))
        `when`(remoteBusDataManager.getBusStopsByQuery(QUERY)).thenReturn(Flowable.empty())

        busDataManager.getBusStopsByQuery(QUERY).subscribe(testSubscriber)

        testSubscriber.assertValue(STOPS)
    }
}