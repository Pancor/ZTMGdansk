package pancor.pl.ztmgdansk.data

import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Route

interface BusDataContract {

    fun onStart()

    fun onStop()

    interface LoadRoutesAndStopsCallback {

        fun onRoutesAndBusStopsLoaded(busStops: List<BusStop>,
                                      routes: List<Route>)

        fun onDataNotAvailable()
    }

    fun getRoutesAndBusStops(callback: LoadRoutesAndStopsCallback)
}