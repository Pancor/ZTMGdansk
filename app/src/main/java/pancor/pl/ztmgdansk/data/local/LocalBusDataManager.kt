package pancor.pl.ztmgdansk.data.local

import io.reactivex.Flowable
import pancor.pl.ztmgdansk.data.local.database.BusDao
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Route
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Singleton
class LocalBusDataManager(private val busDao: BusDao) : LocalBusDataContract {

    private val TIMEOUT_IN_SECONDS = 2L

    override fun getBusRoutes(): Flowable<List<Route>> {
        return busDao.getAllRoutes()
                .timeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
    }

    override fun insertBusRoutes(busRoutes: List<Route>) {
        busDao.insertOrReplaceRoutes(busRoutes)
    }

    override fun getBusStops(): Flowable<List<BusStop>> {
        return busDao.getAllBusStops()
                .timeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
    }

    override fun insertBusStops(busStops: List<BusStop>) {
        busDao.insertOrReplaceBusStops(busStops)
    }

    override fun deleteData() {
        busDao.deleteAllBusRoutes()
        busDao.deleteAllBusStops()
    }
}