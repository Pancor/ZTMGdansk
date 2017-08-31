package pancor.pl.ztmgdansk.base

import android.arch.persistence.room.Room
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import pancor.pl.ztmgdansk.data.BusDataManager
import pancor.pl.ztmgdansk.data.local.database.BusDatabase
import pancor.pl.ztmgdansk.di.DaggerAppComponent
import javax.inject.Inject

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {

        val appComponent = DaggerAppComponent.builder()
                .app(this)
                .build()
        appComponent.inject(this)
        return appComponent
    }
}