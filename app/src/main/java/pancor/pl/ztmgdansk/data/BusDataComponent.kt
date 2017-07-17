package pancor.pl.ztmgdansk.data

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(BusDataModule::class))
interface BusDataComponent {

    fun getBusDataManager() : BusDataManager

    @Component.Builder
    interface Builder {

        fun build(): BusDataComponent
        @BindsInstance fun context(context: Context): Builder
        fun busDataModule(busDataModule: BusDataModule): Builder
    }
}