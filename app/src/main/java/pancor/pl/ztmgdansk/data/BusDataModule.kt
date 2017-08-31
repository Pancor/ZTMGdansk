package pancor.pl.ztmgdansk.data

import dagger.Binds
import dagger.Module
import pancor.pl.ztmgdansk.data.local.LocalBusDataContract
import pancor.pl.ztmgdansk.data.local.LocalBusDataManager
import pancor.pl.ztmgdansk.data.remote.RemoteBusDataManager
import javax.inject.Singleton

@Module
abstract class BusDataModule {

    @Binds
    @Singleton
    abstract fun provideLocalBusDataManager(localSource: LocalBusDataManager) : LocalBusDataContract

    @Binds
    @Singleton
    abstract fun provideRemoteBusDataManager(remoteSource: RemoteBusDataManager) : BusDataContract
}