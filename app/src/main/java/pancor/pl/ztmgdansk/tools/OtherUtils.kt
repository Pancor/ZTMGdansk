package pancor.pl.ztmgdansk.tools

import android.content.Context
import android.util.DisplayMetrics


class OtherUtils {

    fun getPixelsFromDP(context: Context, dp: Int): Int{
        val resources = context.resources
        val displayMetrics = resources.displayMetrics
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }
}