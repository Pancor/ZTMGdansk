package pancor.pl.ztmgdansk.tools.search

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
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

    companion object {
        private const val KEY_DELETE_RIGHT_ICON_VISIBILITY = "key_delete_right_icon_visibility"
        private const val KEY_SUPER_STATE = "key_super_state"
    }

    private lateinit var onBackArrowClickListener: OnBackArrowClickListener
    private var searchQueryTextWatcher: TextWatcher? = null
    private var isDeleteRightIconVisible = false

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
        searchQueryTextWatcher = object: TextWatcher {
            override fun afterTextChanged(p0: Editable) { }

            override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
                subscriber.onNext(text.toString())
                showOrHideTextDeleteIcon()
            }
        }
        searchEditText.addTextChangedListener(searchQueryTextWatcher)
    }

    private fun showOrHideTextDeleteIcon() {
        val searchText = searchEditText.text
        if (searchText.isEmpty()) {
            hideDeleteRightIcon()
        } else {
            showDeleteRightIcon()
        }
    }

    private fun showDeleteRightIcon() {
        isDeleteRightIconVisible = true
        rightIcon.visibility = View.VISIBLE
    }

    private fun hideDeleteRightIcon() {
        isDeleteRightIconVisible = false
        rightIcon.visibility = View.GONE
    }

    fun setOnBackArrowClickListener(listener: OnBackArrowClickListener) {
        onBackArrowClickListener = listener
        setupOnBackArrowClickListener()
    }

    private fun setupOnBackArrowClickListener() {
        leftIcon.setOnClickListener{ onBackArrowClickListener.onBackArrowClick() }
    }

    override fun onSaveInstanceState(): Parcelable {
        val state = Bundle()
        state.putParcelable(KEY_SUPER_STATE, super.onSaveInstanceState())
        state.putBoolean(KEY_DELETE_RIGHT_ICON_VISIBILITY, isDeleteRightIconVisible)
        return state
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        var savedState = state
        if (state is Bundle) {
            savedState = state.getParcelable(KEY_SUPER_STATE)
            isDeleteRightIconVisible = state.getBoolean(KEY_DELETE_RIGHT_ICON_VISIBILITY)
            updateView()
        }
        super.onRestoreInstanceState(savedState)
    }

    private fun updateView() {
        updateDeleteRightIcon()
    }

    private fun updateDeleteRightIcon() {
        if (isDeleteRightIconVisible) {
            showDeleteRightIcon()
        } else {
            hideDeleteRightIcon()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        searchEditText.removeTextChangedListener(searchQueryTextWatcher)
    }

    interface OnBackArrowClickListener {

        fun onBackArrowClick()
    }

    interface OnSearchTextChangedListener {

        fun getSearchTextChangeObservable(): Flowable<String>
    }
}