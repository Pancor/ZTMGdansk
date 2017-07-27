package pancor.pl.ztmgdansk.data.remote.net

import io.reactivex.Single
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Route
import pancor.pl.ztmgdansk.models.ServerResponse
import retrofit2.http.GET

interface NetService {

    @GET("getBusRoutes")
    fun getRoutes(): Single<ServerResponse<Route>>

    @GET("getBusStops")
    fun getBusStops(): Single<ServerResponse<BusStop>>
}