package pancor.pl.ztmgdansk.data.remote

import io.reactivex.Flowable
import pancor.pl.ztmgdansk.data.BusDataContract
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Route
import javax.inject.Singleton

@Singleton
class FakeRemoteBusDataManager : BusDataContract {

    override fun getBusRoutes(): Flowable<List<Route>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBusStops(): Flowable<List<BusStop>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}