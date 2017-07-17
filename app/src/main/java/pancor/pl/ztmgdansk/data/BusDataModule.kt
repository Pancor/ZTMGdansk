package pancor.pl.ztmgdansk.data

import android.content.Context
import dagger.Module
import dagger.Provides
import pancor.pl.ztmgdansk.data.local.LocalBusDataManager
import pancor.pl.ztmgdansk.data.local.LocalScope
import pancor.pl.ztmgdansk.data.remote.RemoteBusDataManager
import pancor.pl.ztmgdansk.data.remote.RemoteScope
import javax.inject.Singleton


@Module class BusDataModule {

    @Provides @Singleton @LocalScope fun provideLocalBusDataManager(context: Context) : BusDataContract {
        return LocalBusDataManager(context)
    }

    @Provides @Singleton @RemoteScope fun provideRemoteBusDataManager() : BusDataContract {
        return RemoteBusDataManager()
    }
}