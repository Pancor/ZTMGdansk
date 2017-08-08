package pancor.pl.ztmgdansk.search_bus

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fr_search_bus.*
import pancor.pl.ztmgdansk.R
import pancor.pl.ztmgdansk.models.SearchResultData

class SearchBusFragment : Fragment(), SearchBusContract.View {

    private lateinit var presenter: SearchBusContract.Presenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fr_search_bus, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val grid = GridLayoutManager(context, 1)
        recyclerView.layoutManager = grid
    }

    override fun setPresenter(presenter: SearchBusContract.Presenter) {
        this.presenter = presenter
    }

    override fun onSearchResult(searchResultData: List<SearchResultData>) {
        recyclerView.adapter = SearchResultAdapter(searchResultData, resources)

    }

    override fun showLoadingIndicator() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    override fun hideLoadingIndicator() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }
}