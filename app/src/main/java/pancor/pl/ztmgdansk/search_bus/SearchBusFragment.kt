package pancor.pl.ztmgdansk.search_bus

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fr_search_bus.*
import pancor.pl.ztmgdansk.R
import pancor.pl.ztmgdansk.base.HEADER_VIEW_TYPE
import pancor.pl.ztmgdansk.models.SearchResultData
import pancor.pl.ztmgdansk.tools.SearchResultItemDecoration

class SearchBusFragment : Fragment(), SearchBusContract.View {

    private lateinit var presenter: SearchBusContract.Presenter
    private lateinit var searchResultInterface: SearchResult

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fr_search_bus, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val grid = GridLayoutManager(context, 4)
        grid.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {

                if (recyclerView.adapter.getItemViewType(position) == HEADER_VIEW_TYPE) {
                    return 4
                } else {
                    return 1
                }
            }
        }
        recyclerView.layoutManager = grid

        val itemDecoration = SearchResultItemDecoration()
        recyclerView.addItemDecoration(itemDecoration)


        val adapter = SearchResultAdapter(resources)
        searchResultInterface = adapter
        recyclerView.adapter = adapter
    }

    override fun setPresenter(presenter: SearchBusContract.Presenter) {
        this.presenter = presenter
    }

    override fun onSearchResult(searchResultData: List<SearchResultData>) {
        searchResultInterface.setData(searchResultData)
    }

    override fun showLoadingIndicator() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    override fun hideLoadingIndicator() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    interface SearchResult {

        fun setData(searchResultData: List<SearchResultData>)
    }
}