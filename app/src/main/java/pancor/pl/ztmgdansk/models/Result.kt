package pancor.pl.ztmgdansk.models

import com.google.gson.annotations.SerializedName


data class Result(@SerializedName("isError") val isError: Boolean,
                  @SerializedName("responseCode") val resultCode: Int,
                  @SerializedName("routes") val routes: List<Route>,
                  @SerializedName("stops") val stops: List<BusStop>) {

    companion object {
        val NO_INTERNET_CONNECTION = 100
        val OK = 12
    }
}