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
import org.mockito.Mockito.`when`
import pancor.pl.ztmgdansk.data.remote.net.InternetConnection
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Result

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
    }

    @Test
    fun getRoutesAndStopsByQueryWhenNoError() {
        setInternetConnection(true)
        val serverResponse = Result(isError = false, resultCode = Result.OK,
                stops = listOf(STOP), routes = listOf(ROUTE))
        `when`(netService.getBusStopsAndRoutesFromSearch(QUERY))
                .thenReturn(Single.just(serverResponse))

        remoteBusDataManager.getBusStopsAndRoutesByQuery(QUERY)
                .subscribe(testSubscriber)

        testSubscriber.assertValue(serverResponse)
    }

    @Test
    fun getRoutesAndStopsByQueryWhenNoInternet() {
        setInternetConnection(false)
        val expectedResponse = Result(isError = true, resultCode = Result.NO_INTERNET_CONNECTION,
                stops = listOf(), routes = listOf())

        remoteBusDataManager.getBusStopsAndRoutesByQuery(QUERY)
                .subscribe(testSubscriber)

        testSubscriber.assertValue(expectedResponse)
    }


    private fun setInternetConnection(isConnected: Boolean) {
        `when`(internetConnection.isInternet()).thenReturn(Flowable.just(isConnected))
    }
}