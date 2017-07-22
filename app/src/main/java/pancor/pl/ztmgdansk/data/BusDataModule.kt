package pancor.pl.ztmgdansk.data

import dagger.Module
import dagger.Provides
import pancor.pl.ztmgdansk.data.local.LocalBusDataContract
import pancor.pl.ztmgdansk.data.local.LocalBusDataManager
import pancor.pl.ztmgdansk.data.local.database.BusDao
import pancor.pl.ztmgdansk.data.remote.RemoteBusDataManager
import pancor.pl.ztmgdansk.data.remote.net.NetService
import javax.inject.Singleton

@Module class BusDataModule {

    @Provides @Singleton fun provideLocalBusDataManager(busDao: BusDao) : LocalBusDataContract {
        return LocalBusDataManager(busDao)
    }

    @Provides @Singleton fun provideRemoteBusDataManager(netService: NetService) : BusDataContract {
        return RemoteBusDataManager(netService)
    }
}