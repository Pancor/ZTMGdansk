package pancor.pl.ztmgdansk.tools

import android.content.Context
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.view_search.view.*
import pancor.pl.ztmgdansk.R
import android.os.AsyncTask.execute
import io.reactivex.*
import pancor.pl.ztmgdansk.R.id.searchInputView

class CustomSearchView : Toolbar {

    lateinit var textObservable: Observable<String>

    constructor(context: Context?) : super(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs, 0)

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

    fun getTextChangeObservable(): Observable<String> {
        return Observable.create<String> { subscriber ->
            run {
                searchInputView.addTextChangedListener(object : TextWatcher {
                    override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                        subscriber.onNext(cs.toString())
                    }

                    override fun beforeTextChanged(s: CharSequence, arg1: Int, arg2: Int, arg3: Int) {

                    }

                    override fun afterTextChanged(arg0: Editable) {

                    }
                })
            }
        }
    }

    interface OnBackNavigationClickListener {
        fun onBackNavigationClickListener()
    }
}