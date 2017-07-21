package pancor.pl.ztmgdansk.data

import dagger.Module
import dagger.Provides
import pancor.pl.ztmgdansk.data.local.LocalBusDataManager
import pancor.pl.ztmgdansk.data.local.LocalScope
import pancor.pl.ztmgdansk.data.local.database.BusDao
import pancor.pl.ztmgdansk.data.remote.RemoteBusDataManager
import pancor.pl.ztmgdansk.data.remote.RemoteScope
import pancor.pl.ztmgdansk.data.remote.net.NetService
import javax.inject.Singleton

@Module class BusDataModule {

    @Provides @Singleton @LocalScope fun provideLocalBusDataManager(busDao: BusDao) : BusDataContract {
        return LocalBusDataManager(busDao)
    }

    @Provides @Singleton @RemoteScope fun provideRemoteBusDataManager(netService: NetService) : BusDataContract {
        return RemoteBusDataManager(netService)
    }
}