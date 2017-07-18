package pancor.pl.ztmgdansk.data.remote

import pancor.pl.ztmgdansk.data.BusDataContract
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
class RemoteBusDataManager(val retrofit: Retrofit) : BusDataContract {

    override fun getRoutesAndStops(callback: BusDataContract.LoadRoutesAndStopsCallback) {

    }

}