package pancor.pl.ztmgdansk.data.local

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import pancor.pl.ztmgdansk.data.local.database.BusDao
import org.mockito.Mockito.verify

class LocalBusDataManagerTest {

    @Mock
    private lateinit var busDao: BusDao

    private lateinit var localBusDataManager: LocalBusDataManager

    @Before
    fun setupLocalBusDataManager() {
        MockitoAnnotations.initMocks(this)
        localBusDataManager = LocalBusDataManager(busDao)
    }

    @Test
    fun clearAllDataFromBusDao() {
        localBusDataManager.deleteData()

        verify(busDao).deleteAllBusRoutes()
        verify(busDao).deleteAllBusStops()
    }
}