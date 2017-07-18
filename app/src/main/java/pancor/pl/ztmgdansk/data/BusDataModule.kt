package pancor.pl.ztmgdansk.data

import android.app.Application
import dagger.Module
import dagger.Provides
import pancor.pl.ztmgdansk.data.local.LocalBusDataManager
import pancor.pl.ztmgdansk.data.local.LocalScope
import pancor.pl.ztmgdansk.data.remote.RemoteBusDataManager
import pancor.pl.ztmgdansk.data.remote.RemoteScope
import retrofit2.Retrofit
import javax.inject.Singleton

@Module class BusDataModule {

    @Provides @Singleton @LocalScope fun provideLocalBusDataManager(app: Application) : BusDataContract {
        val context = app.applicationContext
        return LocalBusDataManager(context)
    }

    @Provides @Singleton @RemoteScope fun provideRemoteBusDataManager(retrofit: Retrofit) : BusDataContract {
        return RemoteBusDataManager(retrofit)
    }
}