package pancor.pl.ztmgdansk.data

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import io.reactivex.subscribers.TestSubscriber
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pancor.pl.ztmgdansk.data.local.database.BusDatabase
import pancor.pl.ztmgdansk.models.Route
import pancor.pl.ztmgdansk.data.local.LocalBusDataManager
import pancor.pl.ztmgdansk.models.BusStop

@RunWith(AndroidJUnit4::class)
@LargeTest
class LocalBusDataManagerTest {

    private val ROUTES = listOf(Route(1, "1", "Route1"), Route(2, "2", "Route2"))
    private val STOPS = listOf(BusStop(1, "Stop1"), BusStop(2, "Stop2"))

    private lateinit var localBusDataManager: LocalBusDataManager
    private val testSubscriber = TestSubscriber<Any>()

    @Before
    fun setupLocalBusDataManager() {
        val db = Room.databaseBuilder(InstrumentationRegistry.getTargetContext(),
                BusDatabase::class.java, "ztm_database").build()
        localBusDataManager = LocalBusDataManager(db.getBusDao())
    }

    @After
    fun cleanUp() {
        localBusDataManager.deleteData()
    }

    @Test
    fun insertRoutesThenGetItBack() {
        localBusDataManager.insertBusRoutes(ROUTES)
        localBusDataManager.getBusRoutes().subscribe(testSubscriber)
        testSubscriber.assertValue(ROUTES)
    }

    @Test
    fun insertBusStopsThenGetItBack() {
        localBusDataManager.insertBusStops(STOPS)
        localBusDataManager.getBusStops().subscribe(testSubscriber)
        testSubscriber.assertValue(STOPS)
    }
}