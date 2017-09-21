package pancor.pl.ztmgdansk.data.cache

import android.util.LruCache
import io.reactivex.Flowable
import pancor.pl.ztmgdansk.data.BusDataContract
import pancor.pl.ztmgdansk.models.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CacheBusDataManager internal constructor(factory: Factory): BusDataContract.Cache {

    @Inject constructor() : this(DefaultFactory)

    private val cache = factory.getCachedResultFromQuery()

    override fun getBusStopsAndRoutesByQuery(query: String): Flowable<Result> {
        val cachedResult = cache.get(query)
        if (cachedResult != null) {
            return cachedResult
        }
        return getEmptyResult()
    }

    private fun getEmptyResult(): Flowable<Result> {
        val result = Result(isError = true, resultCode = Result.NOT_IN_CACHE,
                routes = listOf(), stops = listOf())
        return Flowable.just(result)
    }

    override fun insertResultToCache(query: String, result: Flowable<Result>) {
        if (cache.get(query) == null) {
            cache.put(query, result)
        }
    }

    internal interface Factory {
        fun getCachedResultFromQuery(): LruCache<String, Flowable<Result>>
    }

    private object DefaultFactory: Factory {
        override fun getCachedResultFromQuery(): LruCache<String, Flowable<Result>> = LruCache(50)
    }
}