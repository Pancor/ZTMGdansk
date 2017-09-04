package pancor.pl.ztmgdansk.base

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import pancor.pl.ztmgdansk.BuildConfig
import pancor.pl.ztmgdansk.di.DaggerAppComponent
import timber.log.Timber

class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.builder()
                .app(this)
                .build()
        appComponent.inject(this)
        return appComponent
    }
}