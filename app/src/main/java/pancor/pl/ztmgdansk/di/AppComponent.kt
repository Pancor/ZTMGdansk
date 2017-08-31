package pancor.pl.ztmgdansk.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import pancor.pl.ztmgdansk.base.App
import pancor.pl.ztmgdansk.data.BusDataContract

import pancor.pl.ztmgdansk.data.BusDataManager
import pancor.pl.ztmgdansk.data.BusDataModule
import pancor.pl.ztmgdansk.data.local.database.BusDaoModule

import pancor.pl.ztmgdansk.data.remote.net.NetModule
import pancor.pl.ztmgdansk.tools.schedulers.SchedulerModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidSupportInjectionModule::class, ActivityBindingModule::class,
                             AppModule::class, BusDataModule::class, NetModule::class,
                             SchedulerModule::class, BusDaoModule::class))
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(app: App)

    fun getBusDataManager(): BusDataContract

    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance fun app(app: Application): Builder
    }
}