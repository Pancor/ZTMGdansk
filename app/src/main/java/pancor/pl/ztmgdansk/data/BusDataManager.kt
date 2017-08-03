package pancor.pl.ztmgdansk.data

import io.reactivex.Flowable
import pancor.pl.ztmgdansk.data.local.LocalBusDataContract
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BusDataManager @Inject constructor(val localBusDataManager: LocalBusDataContract,
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

    override fun getBusStopsByQuery(query: String): Flowable<List<BusStop>> {
        val networkSourceWithSave: Flowable<List<BusStop>> = remoteBusDataManager
                .getBusStopsByQuery(query)
                .doOnNext { localBusDataManager.insertBusStops(it) }
                .onErrorReturn { listOf() }
        val localSource: Flowable<List<BusStop>> = localBusDataManager
                .getBusStopsByQuery(query)
                .onErrorReturn { listOf() }
        return localSource.mergeWith(networkSourceWithSave)
                .distinct()
    }

    override fun getBusRoutesByQuery(query: String): Flowable<List<Route>> {
        val networkSourceWithSave: Flowable<List<Route>> = remoteBusDataManager
                .getBusRoutesByQuery(query)
                .doOnNext { localBusDataManager.insertBusRoutes(it) }
                .onErrorReturn { listOf() }
        val localSource: Flowable<List<Route>> = localBusDataManager
                .getBusRoutesByQuery(query)
                .onErrorReturn { listOf() }
        return localSource.mergeWith(networkSourceWithSave)
                .distinct()
    }
}