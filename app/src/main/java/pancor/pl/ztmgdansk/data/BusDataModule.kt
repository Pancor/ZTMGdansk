package pancor.pl.ztmgdansk.data

import dagger.Binds
import dagger.Module
import pancor.pl.ztmgdansk.data.cache.CacheBusDataManager
import pancor.pl.ztmgdansk.data.local.LocalBusDataManager
import pancor.pl.ztmgdansk.data.remote.RemoteBusDataManager
import javax.inject.Singleton

@Module
abstract class BusDataModule {

    @Binds
    @Singleton
    abstract fun provideLocalBusDataManager(localSource: LocalBusDataManager) : BusDataContract.Local

    @Binds
    @Singleton
    abstract fun provideRemoteBusDataManager(remoteSource: RemoteBusDataManager) : BusDataContract

    @Binds
    @Singleton
    abstract fun provideCacheBusDataManager(cachedSource: CacheBusDataManager) : BusDataContract.Cache
}