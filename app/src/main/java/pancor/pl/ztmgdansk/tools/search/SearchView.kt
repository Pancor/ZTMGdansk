package pancor.pl.ztmgdansk.tools.search

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import io.reactivex.*
import kotlinx.android.synthetic.main.view_search.view.*
import pancor.pl.ztmgdansk.R

class SearchView : FrameLayout {

    private lateinit var onBackArrowClickListener: OnBackArrowClickListener

    constructor(context: Context) : super(context, null)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_search, this, true)
        setupLeftIcon()
        setupRightIcon()
    }

    private fun setupLeftIcon() {
        leftIcon.setImageResource(R.drawable.ic_arrow_back_black_24dp) //TODO manage memory usage
    }

    private fun setupRightIcon() {
        rightIcon.setImageResource(R.drawable.ic_close_black_24dp) //TODO manage memory usage
        rightIcon.setOnClickListener{ searchEditText.setText("") }
    }

    fun getTextChangeObservable(): Flowable<String> {
        return Flowable.create({ setupTextChangeSubscriber(it)}, BackpressureStrategy.BUFFER)
    }

    private fun setupTextChangeSubscriber(subscriber: FlowableEmitter<String>) {
        searchEditText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable) { }

            override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
                subscriber.onNext(text.toString())
                showOrHideTextDeleteIcon()
            }
        })
    }

    private fun showOrHideTextDeleteIcon() {
        val searchText = searchEditText.text
        if (searchText.isNotEmpty()) {
            rightIcon.visibility = View.VISIBLE
        } else {
            rightIcon.visibility = View.GONE
        }
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

    interface OnSearchTextChangedListener {

        fun getSearchTextChangeObservable(): Flowable<String>
    }
}