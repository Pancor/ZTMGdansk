package pancor.pl.ztmgdansk.data

import io.reactivex.Flowable
import io.reactivex.Single
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Response
import pancor.pl.ztmgdansk.models.Result
import pancor.pl.ztmgdansk.models.Route

interface BusDataContract {

    fun getBusStopsAndRoutesByQuery(query: String): Flowable<Result>

    interface Local : BusDataContract {

        fun insertBusRoutes(busRoutes: List<Route>)

        fun insertBusStops(busStops: List<BusStop>)

        fun deleteData()
    }
}