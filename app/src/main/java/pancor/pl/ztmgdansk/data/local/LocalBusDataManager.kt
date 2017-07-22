package pancor.pl.ztmgdansk.data.local

import io.reactivex.Flowable
import io.reactivex.Single
import pancor.pl.ztmgdansk.data.BusDataContract
import pancor.pl.ztmgdansk.data.local.database.BusDao
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Route
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Singleton
class LocalBusDataManager(val busDao: BusDao) : BusDataContract {

    val TIMEOUT_IN_SECONDS = 2L

    override fun getBusRoutes(): Flowable<List<Route>> {
        return busDao.getAllRoutes()
                .timeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
    }

    override fun getBusStops(): Flowable<List<BusStop>> {
        return busDao.getAllBusStops()
                .timeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
    }
}