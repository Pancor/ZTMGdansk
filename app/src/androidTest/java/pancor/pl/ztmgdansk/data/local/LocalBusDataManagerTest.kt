package pancor.pl.ztmgdansk.data.local

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.runner.RunWith
import pancor.pl.ztmgdansk.data.local.database.BusDatabase

@RunWith(AndroidJUnit4::class)
class LocalBusDataManagerTest {

    private lateinit var localBusDataManager: LocalBusDataManager

    @Before
    fun setupLocalBusDataManager(){
        val db = Room.databaseBuilder(InstrumentationRegistry.getTargetContext(),
                BusDatabase::class.java, "ztm_database").build()
        localBusDataManager = LocalBusDataManager(db.getBusDao())
    }
}