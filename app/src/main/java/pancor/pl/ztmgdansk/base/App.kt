package pancor.pl.ztmgdansk.base

import android.app.Application
import pancor.pl.ztmgdansk.data.BusDataComponent
import pancor.pl.ztmgdansk.data.BusDataModule
import pancor.pl.ztmgdansk.data.DaggerBusDataComponent
import pancor.pl.ztmgdansk.data.remote.net.NetModule


class App : Application() {

    val busDataComponentBuild = getBusDataComponent()

    override fun onCreate(){
        super.onCreate()

    }

    private fun getBusDataComponent() : BusDataComponent {
        return DaggerBusDataComponent.builder()
                .app(this)
                .busDataModule(BusDataModule())
                .netModule(NetModule())
                .build()
    }
}