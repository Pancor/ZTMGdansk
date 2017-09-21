package pancor.pl.ztmgdansk.data.cache

import android.util.LruCache
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import pancor.pl.ztmgdansk.models.Result

class CacheBusDataManagerTest {

    private lateinit var cacheBusDataManager: CacheBusDataManager

    @Before
    fun setupCacheBusDataManager() {
        cacheBusDataManager = CacheBusDataManager(Factory)
    }
    
    object Factory: CacheBusDataManager.Factory {

        inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)

        override fun getCachedResultFromQuery(): LruCache<String, Flowable<Result>> = mock()
    }
}