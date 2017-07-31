package pancor.pl.ztmgdansk.data

import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
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
        val networkWithSave: Flowable<List<BusStop>> = remoteBusDataManager.getBusStopsByQuery(query)
                .doOnNext { localBusDataManager.insertBusStops(it) }
                .onErrorReturn { listOf() }
        return Flowable.zip(localBusDataManager.getBusStopsByQuery(query),
                            networkWithSave,
                            BiFunction { local, remote -> mergeTwoListsWithoutDuplicates(local, remote) })
    }

    override fun getBusRoutesByQuery(query: String): Flowable<List<Route>> {
        val networkWithSave: Flowable<List<Route>> = remoteBusDataManager.getBusRoutesByQuery(query)
                .doOnNext { localBusDataManager.insertBusRoutes(it) }
                .onErrorReturn { listOf() }
        return Flowable.zip(localBusDataManager.getBusRoutesByQuery(query),
                            networkWithSave,
                            BiFunction { local, remote -> mergeTwoListsWithoutDuplicates(local, remote) })
    }

    private fun <T> mergeTwoListsWithoutDuplicates(list1: List<T>, list2: List<T>): List<T> {
        val set = HashSet(list1)
        set.addAll(list2)
        return set.toList()
    }
}