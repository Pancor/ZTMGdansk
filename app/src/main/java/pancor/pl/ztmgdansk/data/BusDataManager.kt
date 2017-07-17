package pancor.pl.ztmgdansk.data

import pancor.pl.ztmgdansk.data.local.LocalScope
import pancor.pl.ztmgdansk.data.remote.RemoteScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BusDataManager @Inject constructor(@LocalScope val localBusDataManager: BusDataContract,
                                         @RemoteScope val remoteBusDataManager: BusDataContract) : BusDataContract {

    override fun getRoutesAndStops(callback: BusDataContract.LoadRoutesAndStopsCallback) {

    }
}