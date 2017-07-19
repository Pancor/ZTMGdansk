package pancor.pl.ztmgdansk.data.remote

import io.reactivex.Observable
import io.reactivex.Single
import pancor.pl.ztmgdansk.data.BusDataContract
import pancor.pl.ztmgdansk.data.remote.net.NetService
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Route
import javax.inject.Singleton

@Singleton
class RemoteBusDataManager(private val netService: NetService) : BusDataContract {

    override fun getBusRoutes(): Single<List<Route>> {
        return Single.fromObservable { Observable.empty<List<Route>>() }
    }

    override fun getBusStops(): Single<List<BusStop>> {
        return Single.fromObservable { Observable.empty<List<BusStop>>() }
    }
}