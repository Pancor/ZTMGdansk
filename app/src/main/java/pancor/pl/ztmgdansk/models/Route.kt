package pancor.pl.ztmgdansk.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Route(@SerializedName("routeId") @PrimaryKey override val id: Int,
                 @SerializedName("routeShortName") val routeShortName: String,
                 @SerializedName("routeLongName") val routeLongName: String)  : SearchResultData.Model()