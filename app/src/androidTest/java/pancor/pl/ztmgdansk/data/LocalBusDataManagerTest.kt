package pancor.pl.ztmgdansk.data

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import junit.framework.Assert
import org.hamcrest.collection.IsIterableContainingInAnyOrder
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
    private val QUERY = "Query"

    private lateinit var localBusDataManager: LocalBusDataManager
    private lateinit var database: BusDatabase
    private val testSubscriber = TestSubscriber<Any>()

    @Before
    fun setupLocalBusDataManager() {
        database = Room.databaseBuilder(InstrumentationRegistry.getTargetContext(),
                BusDatabase::class.java, "ztm_database").build()
        localBusDataManager = LocalBusDataManager(database.getBusDao())
    }

    @After
    fun cleanUp() {
        localBusDataManager.deleteData()
        database.close()
    }

    /* TODO
    @Test
    fun insertRoutesThenGetItBack() {
        localBusDataManager.insertBusRoutes(ROUTES)

        localBusDataManager.getBusRoutes().subscribe(testSubscriber)

        testSubscriber.awaitTerminalEvent()
        testSubscriber.assertValue(ROUTES)
    }

    @Test
    fun insertBusStopsThenGetItBack() {
        localBusDataManager.insertBusStops(STOPS)

        localBusDataManager.getBusStops().subscribe(testSubscriber)

        testSubscriber.awaitTerminalEvent()
        testSubscriber.assertValue(STOPS)
    }

    @Test
    fun insertTwoTimesTheSameBusStops() {
        localBusDataManager.insertBusStops(STOPS)
        localBusDataManager.insertBusStops(STOPS)

        localBusDataManager.getBusStops().subscribe(testSubscriber)

        testSubscriber.awaitTerminalEvent()
        testSubscriber.assertValue(STOPS)
    }

    @Test
    fun insertTwoTimesTheSameRoutes() {
        localBusDataManager.insertBusRoutes(ROUTES)
        localBusDataManager.insertBusRoutes(ROUTES)

        localBusDataManager.getBusRoutes().subscribe(testSubscriber)

        testSubscriber.awaitTerminalEvent()
        testSubscriber.assertValue(ROUTES)
    }

    @Test
    fun checkIfPercentageCharactersAreAddedToStopsQuery() {
        localBusDataManager.insertBusStops(STOPS)
        val stop_query = "top"

        localBusDataManager.getBusStopsByQuery(stop_query).subscribe(testSubscriber)

        testSubscriber.awaitTerminalEvent()
        testSubscriber.assertValue(STOPS)
    }

    @Test
    fun checkIfPercentageCharactersAreAddedToRoutesQuery() {
        localBusDataManager.insertBusRoutes(ROUTES)
        val routeQuery = "oute"

        localBusDataManager.getBusRoutesByQuery(routeQuery).subscribe(testSubscriber)

        testSubscriber.awaitTerminalEvent()
        testSubscriber.assertValue(ROUTES)
    }

    @Test
    fun getRouteByShortNameByQuery() {
        localBusDataManager.insertBusRoutes(ROUTES)
        val query = "1"
        val expectedResult = listOf(Route(1, "1", "Route1"))

        localBusDataManager.getBusRoutesByQuery(query).subscribe(testSubscriber)

        testSubscriber.awaitTerminalEvent()
        testSubscriber.assertValue(expectedResult)
    }
*/
}