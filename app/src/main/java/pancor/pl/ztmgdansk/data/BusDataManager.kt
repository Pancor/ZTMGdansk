package pancor.pl.ztmgdansk.data

import io.reactivex.Observable
import io.reactivex.Single
import pancor.pl.ztmgdansk.data.local.LocalScope
import pancor.pl.ztmgdansk.data.remote.RemoteScope
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BusDataManager @Inject constructor(@LocalScope val localBusDataManager: BusDataContract,
                                         @RemoteScope val remoteBusDataManager: BusDataContract) : BusDataContract {

    override fun getBusRoutes(): Single<List<Route>> {
        return Single.fromObservable { Observable.empty<List<Route>>() }
    }

    override fun getBusStops(): Single<List<BusStop>> {
        return Single.fromObservable { Observable.empty<List<BusStop>>() }
    }
}