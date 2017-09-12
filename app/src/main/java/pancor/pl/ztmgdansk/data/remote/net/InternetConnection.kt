package pancor.pl.ztmgdansk.data.remote.net

import android.app.Application
import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton
import android.net.ConnectivityManager
import io.reactivex.Flowable

@Singleton
class InternetConnection @Inject constructor(val application: Application) {

    fun isInternet(): Flowable<Boolean> {
        return Flowable.fromCallable{ isOnline() }
    }

    private fun isOnline(): Boolean {
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }
}