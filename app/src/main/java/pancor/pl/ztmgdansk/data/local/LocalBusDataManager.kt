package pancor.pl.ztmgdansk.data.local

import io.reactivex.Flowable
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
        return busDao.getBusStopsByQuery(query)
                .timeout(TIMEOUT_IN_SECONDS, TIME_UNIT, Flowable.just(listOf()))
                .flatMap { stops ->
                    busDao.getRoutesByQuery(query)
                            .timeout(TIMEOUT_IN_SECONDS, TIME_UNIT, Flowable.just(listOf()))
                            .map { Result(isError = false, resultCode = Result.OK, stops = stops,
                                    routes = listOf()) } }
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