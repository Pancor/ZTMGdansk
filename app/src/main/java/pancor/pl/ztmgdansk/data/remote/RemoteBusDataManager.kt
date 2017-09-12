package pancor.pl.ztmgdansk.data.remote

import io.reactivex.Flowable
import io.reactivex.Single
import pancor.pl.ztmgdansk.data.BusDataContract
import pancor.pl.ztmgdansk.data.remote.net.InternetConnection
import pancor.pl.ztmgdansk.data.remote.net.NetService
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Response
import pancor.pl.ztmgdansk.models.Result
import pancor.pl.ztmgdansk.models.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteBusDataManager @Inject constructor(private val netService: NetService,
                                               private val internetConnection: InternetConnection) : BusDataContract {

    override fun getBusRoutes(): Flowable<List<Route>> {
        return  netService.getRoutes()
                    .flatMap { (isError, _, response) ->
                        if (!isError){
                            Single.just(response)
                        } else {
                            Single.just(listOf())
                        }
                    }
                .onErrorReturn { listOf() }
                .toFlowable()
    }

    override fun getBusStops(): Flowable<List<BusStop>> {
        return netService.getBusStops()
                .flatMap { (isError, _, response) ->
                    if (!isError){
                        Single.just(response)
                    } else {
                        Single.just(listOf())
                    }
                }
                .onErrorReturn { listOf() }
                .toFlowable()
    }

    override fun getBusStopsAndRoutesByQuery(query: String): Flowable<Result> {
        return internetConnection.isInternet()
                .switchMap { isConnected ->
                    if (isConnected) {
                        netService.getBusStopsAndRoutesFromSearch(query)
                                .toFlowable()
                    } else {
                        Flowable.just(Result(isError = true, resultCode = Result.NO_INTERNET_CONNECTION,
                                stops = listOf(), routes = listOf()))
                    }
                }
    }

    private fun <T> returnNoInternetConnectionResult(): Flowable<Response<T>> {
        return Flowable.just(Response(isError = true,
                responseCode = Response.NO_INTERNET_CONNECTION, response = listOf()))
    }
}