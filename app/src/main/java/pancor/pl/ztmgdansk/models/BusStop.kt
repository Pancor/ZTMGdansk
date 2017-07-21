package pancor.pl.ztmgdansk.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class BusStop(@PrimaryKey val stopId: Int)