package pancor.pl.ztmgdansk.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class BusStop(@PrimaryKey val stopId: Int,
                   val stopName: String){

    constructor(stopId: Int,
                stopCode: Int,
                stopName: String,
                stopShortName: Int,
                stopDesc: String,
                subName: String,
                date: String,
                latitude: Double,
                longitude: Double,
                zoneId: Int,
                nonPassenger: Int,
                depot: Int,
                ticketZoneBorder: Int,
                onDemand: Int,
                activationDate: String) : this(stopId, stopName)
}
