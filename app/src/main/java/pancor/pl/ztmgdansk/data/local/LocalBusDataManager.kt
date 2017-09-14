package pancor.pl.ztmgdansk.data.local

import android.arch.persistence.room.EmptyResultSetException
import io.reactivex.Flowable
import io.reactivex.Single
import pancor.pl.ztmgdansk.data.BusDataContract
import pancor.pl.ztmgdansk.data.local.database.BusDao
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Result
import pancor.pl.ztmgdansk.models.Route
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalBusDataManager @Inject constructor(private val busDao: BusDao) : BusDataContract.Local {

    private val TIMEOUT_IN_SECONDS = 2L
    private val TIME_UNIT = TimeUnit.SECONDS

    override fun getBusStopsAndRoutesByQuery(query: String): Flowable<Result> {
        return busDao.getBusStopsByQuery("%$query%")
                .onErrorReturn { listOf() }
                .flatMap { busDao.getRoutesByQuery("%$query%")
                            .onErrorReturn { listOf() }
                            .map { routes -> Pair(it, routes) }
                }
                .map { (stops, routes) -> Result(isError = false, resultCode = Result.OK,
                        stops = stops, routes = routes)
                }
                .toFlowable()
    }

    override fun getBusRoutes(): Flowable<List<Route>> {
        return busDao.getAllRoutes()
                .timeout(TIMEOUT_IN_SECONDS, TIME_UNIT)
    }

    override fun insertBusRoutes(busRoutes: List<Route>) {
        busDao.insertOrReplaceRoutes(busRoutes)
    }

    override fun getBusStops(): Flowable<List<BusStop>> {
        return busDao.getAllBusStops()
                .timeout(TIMEOUT_IN_SECONDS, TIME_UNIT)
    }

    override fun insertBusStops(busStops: List<BusStop>) {
        busDao.insertOrReplaceBusStops(busStops)
    }

    override fun deleteData() {
        busDao.deleteAllBusRoutes()
        busDao.deleteAllBusStops()
    }
}