package pancor.pl.ztmgdansk.data.local.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Route

@Dao
interface BusDao {

    @Query("SELECT * FROM Route")
    fun getAllRoutes(): Flowable<List<Route>>

    @Insert
    fun insertRoutes(routes: List<Route>)

    @Query("SELECT * FROM BusStop")
    fun getAllBusStops(): Flowable<List<BusStop>>

    @Insert
    fun insertBusStops(busStops: List<BusStop>)
}