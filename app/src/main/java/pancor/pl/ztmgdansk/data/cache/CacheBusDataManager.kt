package pancor.pl.ztmgdansk.data.cache

import android.util.LruCache
import io.reactivex.Flowable
import pancor.pl.ztmgdansk.data.BusDataContract
import pancor.pl.ztmgdansk.models.Result
import javax.inject.Singleton

@Singleton
class CacheBusDataManager : BusDataContract{

    private val cachedResultFromQuery: LruCache<String, Flowable<Result>> = LruCache(50)

    override fun getBusStopsAndRoutesByQuery(query: String): Flowable<Result> {
        val cachedResult = cachedResultFromQuery.get(query)
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
}