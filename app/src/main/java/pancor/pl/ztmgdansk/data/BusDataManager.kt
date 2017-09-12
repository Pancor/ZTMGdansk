package pancor.pl.ztmgdansk.data

import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Result
import pancor.pl.ztmgdansk.models.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BusDataManager @Inject constructor(val localBusDataManager: BusDataContract.Local,
                                         val remoteBusDataManager: BusDataContract) : BusDataContract {

    override fun getBusRoutes(): Flowable<List<Route>> {
        val networkWithSave: Flowable<List<Route>> = remoteBusDataManager.getBusRoutes()
                .doOnNext { localBusDataManager.insertBusRoutes(it) }
        return Flowable.concat(localBusDataManager.getBusRoutes(),
                               networkWithSave)
                .elementAt(0)
                .toFlowable()
    }

    override fun getBusStops(): Flowable<List<BusStop>> {
        val networkWithSave: Flowable<List<BusStop>> = remoteBusDataManager.getBusStops()
                .doOnNext { localBusDataManager.insertBusStops(it) }
        return Flowable.concat(localBusDataManager.getBusStops(),
                               networkWithSave)
                .elementAt(0)
                .toFlowable()
    }

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