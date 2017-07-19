package pancor.pl.ztmgdansk.data.remote.net

import io.reactivex.Single
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Route
import pancor.pl.ztmgdansk.models.ServerResponse
import retrofit2.http.GET

interface NetService {

    @GET(""/*TODO get www address*/)
    fun getRoutes(): Single<ServerResponse<Route>>

    @GET(""/*TODO get www address*/)
    fun getBusStops(): Single<ServerResponse<BusStop>>
}