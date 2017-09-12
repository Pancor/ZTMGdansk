package pancor.pl.ztmgdansk.models

import com.google.gson.annotations.SerializedName

data class Response<out T>(@SerializedName("isError") val isError: Boolean,
                           @SerializedName("responseCode") val responseCode: Int,
                           @SerializedName("response") val response: List<T>) {

    companion object {
        val NO_INTERNET_CONNECTION = 100
        val OK = 12
    }
}
