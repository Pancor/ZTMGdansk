package pancor.pl.ztmgdansk.data.remote

import io.reactivex.Flowable
import pancor.pl.ztmgdansk.data.BusDataContract
import pancor.pl.ztmgdansk.data.remote.net.InternetConnection
import pancor.pl.ztmgdansk.data.remote.net.NetService
import pancor.pl.ztmgdansk.models.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteBusDataManager @Inject constructor(private val netService: NetService,
                                               private val internetConnection: InternetConnection) : BusDataContract {

    override fun getBusStopsAndRoutesByQuery(query: String): Flowable<Result> {
        return internetConnection.isInternet()
                .switchMap { isConnected ->
                    if (isConnected) {
                        netService.getBusStopsAndRoutesFromSearch(query)
                                .toFlowable()
                    } else {
                        Flowable.just(Result(isError = true, resultCode = Result.NO_INTERNET_CONNECTION))
                    }
                }
    }
}