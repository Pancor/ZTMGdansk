package pancor.pl.ztmgdansk.data.remote

import io.reactivex.Single
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import pancor.pl.ztmgdansk.data.remote.net.NetService
import pancor.pl.ztmgdansk.models.Route
import pancor.pl.ztmgdansk.models.ServerResponse
import org.mockito.Mockito.`when`
import pancor.pl.ztmgdansk.models.BusStop

class RemoteBusDataManagerTest {

    private val ROUTE = Route(1, "1", "Route1")
    private val STOP = BusStop(1, "Stop1")
    private val QUERY = "query123"

    @Mock
    private lateinit var netService: NetService

    private lateinit var remoteBusDataManager: RemoteBusDataManager
    private lateinit var testSubscriber: TestSubscriber<Any>

    @Before
    fun setupRemoteBusDataManager() {
        MockitoAnnotations.initMocks(this)
        remoteBusDataManager = RemoteBusDataManager(netService)
        testSubscriber = TestSubscriber()
    }

    @Test
    fun returnRouteServerResponseWithErrorThenCallOnError() {
        val invalidServerResponse = ServerResponse(isError = true,
                responseCode = 1, response = listOf(ROUTE))
        `when`(netService.getRoutes()).thenReturn(Single.just(invalidServerResponse))

        remoteBusDataManager.getBusRoutes()
                .subscribe(testSubscriber)

        testSubscriber.assertError(Exception::class.java)
    }

    @Test
    fun returnStopServerResponseWithErrorThenCallOnError() {
        val invalidServerResponse = ServerResponse(isError = true,
                responseCode = 1, response = listOf(STOP))
        `when`(netService.getBusStops()).thenReturn(Single.just(invalidServerResponse))

        remoteBusDataManager.getBusStops()
                .subscribe(testSubscriber)

        testSubscriber.assertError(Exception::class.java)
    }

    @Test
    fun getStopServerResponseWhenNoError() {
        val serverResponse = ServerResponse(isError = false, responseCode = 1,
                response = listOf(STOP))
        `when`(netService.getBusStops()).thenReturn(Single.just(serverResponse))

        remoteBusDataManager.getBusStops().subscribe(testSubscriber)

        testSubscriber.assertValue(listOf(STOP))
    }

    @Test
    fun getRouteServerResponseWhenNoError() {
        val serverResponse = ServerResponse(isError = false, responseCode = 1,
                response = listOf(ROUTE))
        `when`(netService.getRoutes()).thenReturn(Single.just(serverResponse))

        remoteBusDataManager.getBusRoutes().subscribe(testSubscriber)

        testSubscriber.assertValue(listOf(ROUTE))
    }

    @Test
    fun getRouteByQueryWhenNoError() {
        val serverResponse = ServerResponse(isError = false, responseCode = 1,
                response = listOf(ROUTE))
        `when`(netService.getBusRoutesByQuery(QUERY)).thenReturn(Single.just(serverResponse))

        remoteBusDataManager.getBusRoutesByQuery(QUERY).subscribe(testSubscriber)

        testSubscriber.assertValue(listOf(ROUTE))
    }

    @Test
    fun getRouteByQueryWhenIsError() {
        val serverResponse = ServerResponse(isError = true, responseCode = 1,
                response = listOf(ROUTE))
        `when`(netService.getBusRoutesByQuery(QUERY)).thenReturn(Single.just(serverResponse))

        remoteBusDataManager.getBusRoutesByQuery(QUERY).subscribe(testSubscriber)

        testSubscriber.assertError(Exception::class.java)
    }

    @Test
    fun getStopByQueryWhenNoError() {
        val serverResponse = ServerResponse(isError = false, responseCode = 1,
                response = listOf(STOP))
        `when`(netService.getBusStopsByQuery(QUERY)).thenReturn(Single.just(serverResponse))

        remoteBusDataManager.getBusStopsByQuery(QUERY).subscribe(testSubscriber)

        testSubscriber.assertValue(listOf(STOP))
    }

    @Test
    fun getStopByQueryWhenIsError() {
        val serverResponse = ServerResponse(isError = true, responseCode = 1,
                response = listOf(STOP))
        `when`(netService.getBusStopsByQuery(QUERY)).thenReturn(Single.just(serverResponse))

        remoteBusDataManager.getBusStopsByQuery(QUERY).subscribe(testSubscriber)

        testSubscriber.assertError(Exception::class.java)
    }
}