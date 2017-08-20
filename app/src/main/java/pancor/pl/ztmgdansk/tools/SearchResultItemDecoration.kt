package pancor.pl.ztmgdansk.tools

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import pancor.pl.ztmgdansk.R

class SearchResultItemDecoration : RecyclerView.ItemDecoration() {

    private var space: Int = -1
    private var NUMBER_OF_COLUMNS = -1

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        setupSpace(parent)
        setupNumberOfColumns(parent)
        addSpaceToView(outRect, view)
    }

    private fun setupSpace(parent: RecyclerView) {
        if (space == -1) {
            val context = parent.context
            val resources = context.resources
            space = resources.getDimension(R.dimen.searchResultRecyclerSpace).toInt()
        }
    }

    private fun setupNumberOfColumns(parent: RecyclerView) {
        if (NUMBER_OF_COLUMNS == -1) {
            if (parent.layoutManager !is GridLayoutManager) {
                throw IllegalArgumentException("SearchResultItemDecoration accepts only GridLayoutManager " +
                        "as a RecyclerView's layout manager")
            }
            val gridManager = parent.layoutManager as GridLayoutManager
            NUMBER_OF_COLUMNS = gridManager.spanCount
        }
    }

    private fun addSpaceToView(outRect: Rect, view: View) {
        val viewParams = view.layoutParams as GridLayoutManager.LayoutParams

        if (viewParams.spanIndex == 0) {
            outRect.left = space
        } else {
            outRect.left = space / 2
        }

        if (viewParams.spanIndex + viewParams.spanSize == NUMBER_OF_COLUMNS) {
            outRect.right = space
        } else {
            outRect.right = space / 2
        }

        outRect.top = space / 2
        outRect.bottom = space / 2
    }
}