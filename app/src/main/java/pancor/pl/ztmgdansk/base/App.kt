package pancor.pl.ztmgdansk.base

import android.app.Application
import android.arch.persistence.room.Room
import pancor.pl.ztmgdansk.data.BusDataComponent
import pancor.pl.ztmgdansk.data.BusDataModule
import pancor.pl.ztmgdansk.data.DaggerBusDataComponent
import pancor.pl.ztmgdansk.data.local.database.BusDatabase
import pancor.pl.ztmgdansk.data.remote.net.NetModule

class App : Application() {

    lateinit var busDataComponentBuild: BusDataComponent

    override fun onCreate(){
        super.onCreate()
        busDataComponentBuild = getBusDataComponent()
    }

    private fun getBusDataComponent() : BusDataComponent {
        val db = Room.databaseBuilder(applicationContext,
                BusDatabase::class.java, "ztm_database").build()

        return DaggerBusDataComponent.builder()
                .app(this)
                .busDao(db.getBusDao())
                .busDataModule(BusDataModule())
                .netModule(NetModule())
                .build()
    }
}