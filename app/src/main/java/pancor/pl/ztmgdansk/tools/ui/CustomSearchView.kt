package pancor.pl.ztmgdansk.tools.ui

import android.content.Context
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.view_search.view.*
import pancor.pl.ztmgdansk.R


class CustomSearchView : Toolbar {

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_search, this, true)
    }

    fun setBackNavigationListener(listener: OnBackNavigationClickListener){
        toolbar.setNavigationOnClickListener {
            listener.onBackNavigationClickListener()
        }
    }

    interface OnBackNavigationClickListener {
        fun onBackNavigationClickListener()
    }
}