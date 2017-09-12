package pancor.pl.ztmgdansk.tools

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.DisplayMetrics
import android.net.NetworkInfo
import android.net.ConnectivityManager
import io.reactivex.Flowable
import io.reactivex.Observable

class OtherUtils {

    companion object {
        fun isInternetOn(context: Context): Flowable<Boolean> {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return Flowable.just(activeNetworkInfo != null && activeNetworkInfo.isConnected)
        }
    }

    fun getPixelsFromDP(context: Context, dp: Int): Int{
        val resources = context.resources
        val displayMetrics = resources.displayMetrics
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    fun addFragmentToActivity(fragmentManager: FragmentManager, fragment: Fragment,
                              fragmentId: Int){
        val transaction = fragmentManager.beginTransaction()
        transaction.add(fragmentId, fragment)
        transaction.commit()
    }
}