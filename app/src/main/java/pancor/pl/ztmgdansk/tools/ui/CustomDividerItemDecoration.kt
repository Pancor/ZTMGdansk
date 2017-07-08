package pancor.pl.ztmgdansk.tools.ui

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class CustomDividerItemDecoration(val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?,
                                state: RecyclerView.State?) {
        val position = parent?.getChildAdapterPosition(view)
        if (position == 0){
            outRect?.top = space
        }
    }
}