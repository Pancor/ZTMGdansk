package pancor.pl.ztmgdansk.data.remote

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import pancor.pl.ztmgdansk.data.BusDataContract
import pancor.pl.ztmgdansk.data.remote.net.NetService
import javax.inject.Singleton

@Singleton
class RemoteBusDataManager(private val netService: NetService) : BusDataContract {

    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun getRoutesAndBusStops(callback: BusDataContract.LoadRoutesAndStopsCallback) {
        val routesAndBusStopsSingle: Single<Unit> = Single.zip(netService.getBusStops(),
                           netService.getRoutes(),
                           BiFunction { busStopsResponse, routesResponse ->
                               if (!busStopsResponse.isError && !routesResponse.isError){
                                   callback.onRoutesAndBusStopsLoaded(busStopsResponse.response,
                                           routesResponse.response)
                               } else {
                                   callback.onDataNotAvailable()
                               }
                           })
        disposable.add(routesAndBusStopsSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    override fun onStart() {

    }

    override fun onStop() {
        disposable.dispose()
    }
}