package pancor.pl.ztmgdansk.data

import dagger.Component
import pancor.pl.ztmgdansk.base.AppModule
import pancor.pl.ztmgdansk.data.BusDataManager
import pancor.pl.ztmgdansk.data.BusDataModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(BusDataModule::class, AppModule::class))
interface BusDataComponent {

    fun getBusDataManager() : BusDataManager
}