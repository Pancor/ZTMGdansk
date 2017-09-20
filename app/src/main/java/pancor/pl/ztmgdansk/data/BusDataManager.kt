package pancor.pl.ztmgdansk.data

import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import pancor.pl.ztmgdansk.models.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BusDataManager @Inject constructor(val localBusDataManager: BusDataContract.Local,
                                         val cacheBusDataManager: BusDataContract.Cache,
                                         val remoteBusDataManager: BusDataContract) : BusDataContract {

    override fun getBusStopsAndRoutesByQuery(query: String): Flowable<Result> {
        val networkSourceWithSave: Flowable<Result> = remoteBusDataManager
                .getBusStopsAndRoutesByQuery(query)
                .doOnNext { result ->
                    if (!result.isError) {
                        localBusDataManager.insertBusRoutes(result.routes)
                        localBusDataManager.insertBusStops(result.stops)
                    }
                }
        val localSource: Flowable<Result> = localBusDataManager.getBusStopsAndRoutesByQuery(query)
        return Flowable.zip(localSource, networkSourceWithSave, BiFunction { local, remote ->
            val stops = local.stops
                    .plus(remote.stops)
                    .distinct()
            val routes = local.routes
                    .plus(remote.routes)
                    .distinct()
            Result(isError = remote.isError, resultCode = remote.resultCode, stops = stops,
                    routes = routes)
        })
    }
}