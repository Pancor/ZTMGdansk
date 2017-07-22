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
}