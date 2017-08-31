package pancor.pl.ztmgdansk.data.remote

import io.reactivex.Flowable
import io.reactivex.Single
import pancor.pl.ztmgdansk.data.BusDataContract
import pancor.pl.ztmgdansk.data.remote.net.NetService
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteBusDataManager @Inject constructor(private val netService: NetService) : BusDataContract {

    override fun getBusRoutes(): Flowable<List<Route>> {
        return netService.getRoutes()
                .flatMap { (isError, _, response) ->
                    if (!isError){
                        Single.just(response)
                    } else {
                        Single.error(Exception("Server connection error"))
                    }
                }
                .toFlowable()
    }

    override fun getBusStops(): Flowable<List<BusStop>> {
        return netService.getBusStops()
                .flatMap { (isError, _, response) ->
                    if (!isError){
                        Single.just(response)
                    } else {
                        Single.error(Exception("Server connection error"))
                    }
                }
                .toFlowable()
    }

    override fun getBusStopsByQuery(query: String): Flowable<List<BusStop>> {
        return netService.getBusStopsByQuery(query)
                .flatMap { (isError, _, response) ->
                    if (!isError){
                        Single.just(response)
                    } else {
                        Single.error(Exception("Server connection error"))
                    }
                }
                .toFlowable()
    }

    override fun getBusRoutesByQuery(query: String): Flowable<List<Route>> {
        return netService.getBusRoutesByQuery(query)
                .flatMap { (isError, _, response) ->
                    if (!isError){
                        Single.just(response)
                    } else {
                        Single.error(Exception("Server connection error"))
                    }
                }
                .toFlowable()
    }
}