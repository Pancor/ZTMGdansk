package pancor.pl.ztmgdansk.data

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import pancor.pl.ztmgdansk.data.local.database.BusDao
import pancor.pl.ztmgdansk.data.remote.net.NetModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(BusDataModule::class, NetModule::class))
interface BusDataComponent {

    fun getBusDataManager() : BusDataContract

    @Component.Builder
    interface Builder {

        fun build(): BusDataComponent
        @BindsInstance fun app(app: Application): Builder
        @BindsInstance fun busDao(busDao: BusDao): Builder
        fun busDataModule(busDataModule: BusDataModule): Builder
        fun netModule(netModule: NetModule): Builder
    }
}