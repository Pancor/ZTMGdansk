package pancor.pl.ztmgdansk.tools

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.DisplayMetrics


class OtherUtils {

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