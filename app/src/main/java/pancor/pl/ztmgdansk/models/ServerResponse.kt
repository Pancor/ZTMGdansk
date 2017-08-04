package pancor.pl.ztmgdansk.models

import com.google.gson.annotations.SerializedName


data class ServerResponse<out T>(@SerializedName("isError") val isError: Boolean,
                                 @SerializedName("responseCode") val responseCode: Int,
                                 @SerializedName("response") val response: List<T>)
