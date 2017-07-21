package pancor.pl.ztmgdansk.data.local

import io.reactivex.Observable
import io.reactivex.Single
import pancor.pl.ztmgdansk.data.BusDataContract
import pancor.pl.ztmgdansk.data.local.database.BusDao
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Route
import javax.inject.Singleton

@Singleton
class LocalBusDataManager(val busDao: BusDao) : BusDataContract {

    override fun getBusRoutes(): Single<List<Route>> {
        return Single.fromObservable { Observable.empty<List<Route>>() }
    }

    override fun getBusStops(): Single<List<BusStop>> {
        return Single.fromObservable { Observable.empty<List<BusStop>>() }
    }
}