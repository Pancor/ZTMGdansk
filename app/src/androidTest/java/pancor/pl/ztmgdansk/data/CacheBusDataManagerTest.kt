package pancor.pl.ztmgdansk.data

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pancor.pl.ztmgdansk.data.cache.CacheBusDataManager
import pancor.pl.ztmgdansk.models.Result

@RunWith(AndroidJUnit4::class)
@LargeTest
class CacheBusDataManagerTest {

    private lateinit var cacheBusDataManager: CacheBusDataManager
    private lateinit var testSubscriber: TestSubscriber<Any>

    @Before
    fun setupCacheBusDataManager() {
        testSubscriber = TestSubscriber()
        cacheBusDataManager = CacheBusDataManager()
    }

    @Test
    fun insertDataToCacheThenGetItBack() {
        val result = Result(isError = false, resultCode = Result.OK, routes = listOf(), stops = listOf())
        val query ="query"

        cacheBusDataManager.insertResultToCache(query, Flowable.just(result))
        cacheBusDataManager.getBusStopsAndRoutesByQuery(query)
                .subscribe(testSubscriber)

        testSubscriber.assertValue(result)
    }
}