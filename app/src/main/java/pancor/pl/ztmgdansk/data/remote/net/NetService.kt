package pancor.pl.ztmgdansk.data.remote.net

import io.reactivex.Single
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Route
import pancor.pl.ztmgdansk.models.Response
import pancor.pl.ztmgdansk.models.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface NetService {

    @GET("getBusRoutes")
    fun getRoutes(): Single<Response<Route>>

    @GET("getBusStops")
    fun getBusStops(): Single<Response<BusStop>>

    @GET("getStopsFromSearch")
    fun getBusStopsByQuery(@Query("search") query: String): Single<Response<BusStop>>

    @GET("getRoutesFromSearch")
    fun getBusRoutesByQuery(@Query("search") query: String): Single<Response<Route>>

    @GET("getBusStopsAndRoutesFromSearch")
    fun getBusStopsAndRoutesFromSearch(@Query("search") query: String): Single<Result>
}