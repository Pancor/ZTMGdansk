package pancor.pl.ztmgdansk.data.remote

import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import pancor.pl.ztmgdansk.data.remote.net.NetService
import pancor.pl.ztmgdansk.models.Route
import pancor.pl.ztmgdansk.models.Response
import org.mockito.Mockito.`when`
import pancor.pl.ztmgdansk.data.remote.net.InternetConnection
import pancor.pl.ztmgdansk.models.BusStop

class RemoteBusDataManagerTest {

    private val ROUTE = Route(1, "1", "Route1")
    private val STOP = BusStop(1, "Stop1")
    private val QUERY = "query123"

    @Mock
    private lateinit var netService: NetService

    @Mock
    private lateinit var internetConnection: InternetConnection

    private lateinit var remoteBusDataManager: RemoteBusDataManager
    private lateinit var testSubscriber: TestSubscriber<Any>

    @Before
    fun setupRemoteBusDataManager() {
        MockitoAnnotations.initMocks(this)
        remoteBusDataManager = RemoteBusDataManager(netService, internetConnection)
        testSubscriber = TestSubscriber()

        `when`(internetConnection.isInternet()).thenReturn(Flowable.just(true)) //TODO  you now what to do
    }

    /* TODO
    @Test
    fun returnRouteServerResponseWithErrorThenCallOnError() {
        val invalidServerResponse = Response(isError = true,
                responseCode = 1, response = listOf(ROUTE))
        `when`(netService.getRoutes()).thenReturn(Single.just(invalidServerResponse))

        remoteBusDataManager.getBusRoutes()
                .subscribe(testSubscriber)

        testSubscriber.assertError(Exception::class.java)
    }

    @Test
    fun returnStopServerResponseWithErrorThenCallOnError() {
        val invalidServerResponse = Response(isError = true,
                responseCode = 1, response = listOf(STOP))
        `when`(netService.getBusStops()).thenReturn(Single.just(invalidServerResponse))

        remoteBusDataManager.getBusStops()
                .subscribe(testSubscriber)

        testSubscriber.assertError(Exception::class.java)
    }

    @Test
    fun getStopServerResponseWhenNoError() {
        val serverResponse = Response(isError = false, responseCode = 1,
                response = listOf(STOP))
        `when`(netService.getBusStops()).thenReturn(Single.just(serverResponse))

        remoteBusDataManager.getBusStops().subscribe(testSubscriber)

        testSubscriber.assertValue(listOf(STOP))
    }

    @Test
    fun getRouteServerResponseWhenNoError() {
        val serverResponse = Response(isError = false, responseCode = 1,
                response = listOf(ROUTE))
        `when`(netService.getRoutes()).thenReturn(Single.just(serverResponse))

        remoteBusDataManager.getBusRoutes().subscribe(testSubscriber)

        testSubscriber.assertValue(listOf(ROUTE))
    }

    @Test
    fun getRouteByQueryWhenNoError() {
        val serverResponse = Response(isError = false, responseCode = 1,
                response = listOf(ROUTE))
        `when`(netService.getBusRoutesByQuery(QUERY)).thenReturn(Single.just(serverResponse))

        remoteBusDataManager.getBusRoutesByQuery(QUERY).subscribe(testSubscriber)

        testSubscriber.assertValue(listOf(ROUTE))
    }

    @Test
    fun getRouteByQueryWhenIsError() {
        val serverResponse = Response(isError = true, responseCode = 1,
                response = listOf(ROUTE))
        `when`(netService.getBusRoutesByQuery(QUERY)).thenReturn(Single.just(serverResponse))

        remoteBusDataManager.getBusRoutesByQuery(QUERY).subscribe(testSubscriber)

        testSubscriber.assertError(Exception::class.java)
    }

    @Test
    fun getStopByQueryWhenNoError() {
        val serverResponse = Response(isError = false, responseCode = 1,
                response = listOf(STOP))
        `when`(netService.getBusStopsByQuery(QUERY)).thenReturn(Single.just(serverResponse))

        remoteBusDataManager.getBusStopsByQuery(QUERY).subscribe(testSubscriber)

        testSubscriber.assertValue(listOf(STOP))
    }

    @Test
    fun getStopByQueryWhenIsError() {
        val serverResponse = Response(isError = true, responseCode = 1,
                response = listOf(STOP))
        `when`(netService.getBusStopsByQuery(QUERY)).thenReturn(Single.just(serverResponse))

        remoteBusDataManager.getBusStopsByQuery(QUERY).subscribe(testSubscriber)

        testSubscriber.assertError(Exception::class.java)
    } */
}