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
import pancor.pl.ztmgdansk.models.Result

@RunWith(AndroidJUnit4::class)
@LargeTest
class LocalBusDataManagerTest {

    private val ROUTES = listOf(Route(1, "10", "Route"), Route(2, "2", "Route2"))
    private val STOPS = listOf(BusStop(1, "Stop1"), BusStop(2, "Stop2"))

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

    @Test
    fun insertRouteThenGetItBack() {
        localBusDataManager.insertBusRoutes(ROUTES)
        val query = "Route"
        val expectedResult = Result(isError = false, resultCode = Result.OK,
                routes = ROUTES, stops = listOf())

        localBusDataManager.getBusStopsAndRoutesByQuery(query)
                .subscribe(testSubscriber)

        testSubscriber.awaitTerminalEvent()
        testSubscriber.assertValue(expectedResult)
    }

    @Test
    fun checkIfPercentageCharactersAreAddedToQuery() {
        localBusDataManager.insertBusStops(STOPS)
        val stop_query = "top"
        val expectedResult = Result(isError = false, resultCode = Result.OK,
                routes = listOf(), stops = STOPS)

        localBusDataManager.getBusStopsAndRoutesByQuery(stop_query)
                .subscribe(testSubscriber)

        testSubscriber.awaitTerminalEvent()
        testSubscriber.assertValue(expectedResult)
    }

    @Test
    fun getRouteByShortNameByQuery() {
        localBusDataManager.insertBusRoutes(ROUTES)
        val query = "10"
        val expectedResult = Result(isError = false, resultCode = Result.OK,
                routes = listOf(Route(1, "10", "Route")), stops = listOf())

        localBusDataManager.getBusStopsAndRoutesByQuery(query)
                .subscribe(testSubscriber)

        testSubscriber.awaitTerminalEvent()
        testSubscriber.assertValue(expectedResult)
    }
}