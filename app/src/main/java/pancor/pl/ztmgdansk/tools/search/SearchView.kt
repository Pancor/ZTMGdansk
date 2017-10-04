package pancor.pl.ztmgdansk.tools.search

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_search.view.*
import pancor.pl.ztmgdansk.R


class SearchView : FrameLayout {

    private lateinit var onBackArrowClickListener: OnBackArrowClickListener

    constructor(context: Context) : super(context, null)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        initSearchView()
    }

    private fun initSearchView() {
        LayoutInflater.from(context).inflate(R.layout.view_search, this, true)
        setupLeftIcon()
        setupRightIcon()
    }

    private fun setupLeftIcon() {
        Glide.with(context)
                .load(R.drawable.ic_arrow_back_black_24dp)
                .into(leftIcon)
    }

    private fun setupRightIcon() {
        Glide.with(context)
                .load(R.drawable.ic_close_black_24dp)
                .centerCrop()
                .into(rightIcon)
    }

    fun setOnBackArrowClickListener(listener: OnBackArrowClickListener) {
        onBackArrowClickListener = listener
        setupOnBackArrowClickListener()
    }

    private fun setupOnBackArrowClickListener() {
        leftIcon.setOnClickListener{ onBackArrowClickListener.onBackArrowClick() }
    }

    interface OnBackArrowClickListener {

        fun onBackArrowClick()
    }
}