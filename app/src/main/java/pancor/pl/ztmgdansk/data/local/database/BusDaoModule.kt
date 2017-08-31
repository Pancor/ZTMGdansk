package pancor.pl.ztmgdansk.data.local.database

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class BusDaoModule {

    @Provides
    @Singleton fun provideBusDao(context: Context): BusDao {
        val db = Room.databaseBuilder(context,
                BusDatabase::class.java,
                "ztm_database")
                .build()
        return db.getBusDao()
    }
}