package pancor.pl.ztmgdansk.data.cache

import android.util.LruCache
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import pancor.pl.ztmgdansk.models.Result

class CacheBusDataManagerTest {

    private lateinit var cacheBusDataManager: CacheBusDataManager
    private lateinit var cache: LruCache<String, Flowable<Result>>
    private lateinit var testSubscriber: TestSubscriber<Any>

    @Before
    fun setupCacheBusDataManager() {
        testSubscriber = TestSubscriber()
        cacheBusDataManager = CacheBusDataManager(object: CacheBusDataManager.Factory {
            inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)
            override fun getCachedResultFromQuery(): LruCache<String, Flowable<Result>> {
                cache = mock()
                return cache
            }
        })
    }

    @Test
    fun whenIsNotMatchingResultForQueryThenInsertOne() {
        val query = "query"
        val result = Flowable.just(Result(isError = false, resultCode = Result.OK,
                routes = listOf(), stops = listOf()))

        `when`(cache.get(query)).thenReturn(null)
        cacheBusDataManager.insertResultToCache(query, result)

        verify(cache, times(1)).put(query, result)
    }

    @Test
    fun whenIsMatchingResultForQueryThenDoNotInsertSecondOne() {
        val query = "query"
        val result = Flowable.just(Result(isError = false, resultCode = Result.OK,
                routes = listOf(), stops = listOf()))

        `when`(cache.get(query)).thenReturn(result)
        cacheBusDataManager.insertResultToCache(query, result)

        verify(cache, times(0)).put(query, result)
    }

    @Test
    fun whenThereIsNoMatchingResultThenReturnEmptyResult() {
        val query = "query"
        val emptyResult = Result(isError = true, resultCode = Result.NOT_IN_CACHE,
                routes = listOf(), stops = listOf())
        `when`(cache.get(query)).thenReturn(null)

        cacheBusDataManager.getBusStopsAndRoutesByQuery(query)
                .subscribe(testSubscriber)

        testSubscriber.assertValue(emptyResult)
    }

    @Test
    fun whenThereIsMatchingResultThenReturnIt() {
        val query = "query"
        val result = Result(isError = false, resultCode = Result.OK,
                routes = listOf(), stops = listOf())
        `when`(cache.get(query)).thenReturn(Flowable.just(result))

        cacheBusDataManager.getBusStopsAndRoutesByQuery(query)
                .subscribe(testSubscriber)

        testSubscriber.assertValue(result)
    }
}