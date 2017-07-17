package pancor.pl.ztmgdansk.data.local

import android.content.Context
import pancor.pl.ztmgdansk.data.BusDataContract
import javax.inject.Singleton

@Singleton
class LocalBusDataManager(val context: Context) : BusDataContract {

    override fun getRoutesAndStops(callback: BusDataContract.LoadRoutesAndStopsCallback) {
        
    }
}