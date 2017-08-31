package pancor.pl.ztmgdansk.base

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import pancor.pl.ztmgdansk.di.DaggerAppComponent

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.builder()
                .app(this)
                .build()
        appComponent.inject(this)
        return appComponent
    }
}