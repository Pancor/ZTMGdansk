package pancor.pl.ztmgdansk.data.local.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Route

@Database(entities = arrayOf(Route::class, BusStop::class),
          version = 1)
abstract class BusDatabase : RoomDatabase() {

    abstract fun getBusDao(): BusDao
}