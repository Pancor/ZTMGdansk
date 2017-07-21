package pancor.pl.ztmgdansk.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Route(@PrimaryKey val routeId: Int,
                 val routeShortName: String,
                 val routeLongName: String)