package pancor.pl.ztmgdansk.search_bus

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pancor.pl.ztmgdansk.R

class SearchBusFragment : Fragment(), SearchBusContract.View {

    private lateinit var presenter: SearchBusContract.Presenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fr_search_bus, container, false)
    }

    override fun setPresenter(presenter: SearchBusContract.Presenter) {
        this.presenter = presenter
    }

    override fun showLoadingIndicator() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoadingIndicator() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}