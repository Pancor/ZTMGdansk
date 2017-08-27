package pancor.pl.ztmgdansk.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class BusStop(@SerializedName("stopId") @PrimaryKey override val id: Int,
                   @SerializedName("stopName") val stopName: String,
                   @SerializedName("stopCode") val stopCode: Int?,
                   @SerializedName("stopShortName") val stopShortName: Int?,
                   @SerializedName("stopDesc") val stopDesc: String?,
                   @SerializedName("subName") val subName: String?,
                   @SerializedName("date") val date: String?,
                   @SerializedName("latitude") val latitude: Double?,
                   @SerializedName("longitude") val longitude: Double?,
                   @SerializedName("zoneId") val zoneId: Int?,
                   @SerializedName("nonPassenger") val nonPassenger: Int?,
                   @SerializedName("depot") val depot: Int?,
                   @SerializedName("ticketZoneBorder") val ticketZoneBorder: Int?,
                   @SerializedName("onDemand") val onDemand: Int?,
                   @SerializedName("activationDate") val activationDate: String?)  : SearchResultData.Model(){

    //TODO change it!
    @Ignore constructor(id: Int,
                        stopName: String) : this(id, stopName, null, null, null, null, null,
                                         null, null, null, null, null, null, null, null)
}
