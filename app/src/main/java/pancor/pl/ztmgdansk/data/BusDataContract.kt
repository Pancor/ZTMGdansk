package pancor.pl.ztmgdansk.data

import io.reactivex.Flowable
import io.reactivex.Single
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Route

interface BusDataContract {

    fun getBusRoutes(): Flowable<List<Route>>

    fun getBusStops(): Flowable<List<BusStop>>
}