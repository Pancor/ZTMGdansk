package pancor.pl.ztmgdansk.base

import android.app.Application
import pancor.pl.ztmgdansk.data.BusDataComponent
import pancor.pl.ztmgdansk.data.BusDataModule
import pancor.pl.ztmgdansk.data.DaggerBusDataComponent


class App : Application() {

    val busDataComponentBuild = getBusDataComponent()

    override fun onCreate(){
        super.onCreate()

    }

    private fun getBusDataComponent() : BusDataComponent {
        return DaggerBusDataComponent.builder()
                .appModule(AppModule(applicationContext))
                .busDataModule(BusDataModule())
                .build()
    }
}