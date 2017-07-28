package pancor.pl.ztmgdansk.data.local

import pancor.pl.ztmgdansk.data.BusDataContract
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Route


interface LocalBusDataContract : BusDataContract {

    fun insertBusRoutes(busRoutes: List<Route>)

    fun insertBusStops(busStops: List<BusStop>)

    fun deleteData()
}