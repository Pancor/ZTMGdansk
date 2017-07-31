package pancor.pl.ztmgdansk.data.local.database

import android.arch.persistence.room.*
import io.reactivex.Flowable
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Route

@Dao
interface BusDao {

    @Query("SELECT * FROM Route;")
    fun getAllRoutes(): Flowable<List<Route>>

    @Query("SELECT * FROM Route WHERE routeShortName LIKE :query " +
            "OR routeLongName LIKE :query;")
    fun getRoutesByQuery(query: String): Flowable<List<Route>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplaceRoutes(routes: List<Route>)

    @Query("DELETE FROM Route;")
    fun deleteAllBusRoutes()

    @Query("SELECT * FROM BusStop;")
    fun getAllBusStops(): Flowable<List<BusStop>>

    @Query("SELECT * FROM BusStop WHERE stopName LIKE :query;")
    fun getBusStopsByQuery(query: String): Flowable<List<BusStop>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplaceBusStops(busStops: List<BusStop>)

    @Query("DELETE FROM BusStop;")
    fun deleteAllBusStops()
}