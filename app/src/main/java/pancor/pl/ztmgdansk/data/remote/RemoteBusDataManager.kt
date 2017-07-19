package pancor.pl.ztmgdansk.data.remote

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import pancor.pl.ztmgdansk.data.BusDataContract
import pancor.pl.ztmgdansk.data.remote.net.NetService
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Route
import javax.inject.Singleton

@Singleton
class RemoteBusDataManager(private val netService: NetService) : BusDataContract {

    override fun getBusRoutes(): Single<List<Route>> {
        return netService.getRoutes()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap { (isError, _, response) ->
                    if (!isError){
                        Single.just(response)
                    } else {
                        Single.error(Exception("Server connection error"))
                    }
                }
    }

    override fun getBusStops(): Single<List<BusStop>> {
        return netService.getBusStops()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap { (isError, _, response) ->
                    if (!isError){
                        Single.just(response)
                    } else {
                        Single.error(Exception("Server connection error"))
                    }
                }
    }
}