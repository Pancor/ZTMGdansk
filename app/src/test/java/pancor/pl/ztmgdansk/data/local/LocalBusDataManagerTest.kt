package pancor.pl.ztmgdansk.data.local

import android.arch.persistence.room.EmptyResultSetException
import io.reactivex.Single
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import pancor.pl.ztmgdansk.data.local.database.BusDao
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import pancor.pl.ztmgdansk.models.Result

class LocalBusDataManagerTest {

    @Mock
    private lateinit var busDao: BusDao

    private lateinit var localBusDataManager: LocalBusDataManager
    private lateinit var testSubscriber: TestSubscriber<Any>

    @Before
    fun setupLocalBusDataManager() {
        MockitoAnnotations.initMocks(this)
        testSubscriber = TestSubscriber()
        localBusDataManager = LocalBusDataManager(busDao)
    }

    @Test
    fun clearAllDataFromBusDao() {
        localBusDataManager.deleteData()

        verify(busDao).deleteAllBusRoutes()
        verify(busDao).deleteAllBusStops()
    }

    @Test
    fun getBusStopsWhenErrorOccurredThenReturnEmptyList() {
        `when`(busDao.getBusStopsByQuery("%%")).thenReturn(Single.error(EmptyResultSetException("")))
        `when`(busDao.getRoutesByQuery("%%")).thenReturn(Single.just(listOf()))
        val expectedResponse = Result(isError = false, resultCode = Result.OK,
                stops = listOf(), routes = listOf())

        localBusDataManager.getBusStopsAndRoutesByQuery("")
                .subscribe(testSubscriber)

        testSubscriber.assertValues(expectedResponse)
    }

    @Test
    fun getRoutesWhenErrorOccurredThenReturnEmptyList() {
        `when`(busDao.getBusStopsByQuery("%%")).thenReturn(Single.just(listOf()))
        `when`(busDao.getRoutesByQuery("%%")).thenReturn(Single.error(EmptyResultSetException("")))
        val expectedResponse = Result(isError = false, resultCode = Result.OK,
                stops = listOf(), routes = listOf())

        localBusDataManager.getBusStopsAndRoutesByQuery("")
                .subscribe(testSubscriber)

        testSubscriber.assertValues(expectedResponse)
    }
}