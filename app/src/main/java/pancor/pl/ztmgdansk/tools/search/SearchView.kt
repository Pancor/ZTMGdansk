package pancor.pl.ztmgdansk.tools.search

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import pancor.pl.ztmgdansk.R


class SearchView : FrameLayout {

    constructor(context: Context) : super(context, null)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        initSearchView()
    }

    private fun initSearchView() {
        LayoutInflater.from(context).inflate(R.layout.view_search, this, true)
    }
}