package pancor.pl.ztmgdansk.models


data class ServerResponse<out T>(val isError: Boolean, val responseCode: Int,
                                 val response: List<T>)
