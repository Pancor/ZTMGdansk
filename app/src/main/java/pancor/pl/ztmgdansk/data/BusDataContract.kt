package pancor.pl.ztmgdansk.data

import io.reactivex.Single
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Route

interface BusDataContract {

    fun getBusRoutes(): Single<List<Route>>

    fun getBusStops(): Single<List<BusStop>>
}