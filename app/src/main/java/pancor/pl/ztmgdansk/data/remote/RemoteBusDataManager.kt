package pancor.pl.ztmgdansk.data.remote

import pancor.pl.ztmgdansk.data.BusDataContract
import javax.inject.Singleton

@Singleton
class RemoteBusDataManager : BusDataContract {

    override fun getRoutesAndStops(callback: BusDataContract.LoadRoutesAndStopsCallback) {

    }

}