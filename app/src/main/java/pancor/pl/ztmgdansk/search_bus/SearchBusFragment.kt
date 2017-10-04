package pancor.pl.ztmgdansk.search_bus

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fr_search_bus.*
import pancor.pl.ztmgdansk.R
import pancor.pl.ztmgdansk.base.BUS_STOP_VIEW_TYPE
import pancor.pl.ztmgdansk.base.BaseFragment
import pancor.pl.ztmgdansk.base.HEADER_VIEW_TYPE
import pancor.pl.ztmgdansk.base.ROUTE_VIEW_TYPE
import pancor.pl.ztmgdansk.models.SearchResultData
import pancor.pl.ztmgdansk.tools.SearchResultItemDecoration
import pancor.pl.ztmgdansk.tools.search.SearchView
import javax.inject.Inject

class SearchBusFragment @Inject constructor(): BaseFragment(), SearchBusContract.View {

    @Inject
    lateinit var presenter: SearchBusContract.Presenter
    @Inject
    lateinit var adapter: SearchResultAdapter
    private lateinit var searchResultInterface: SearchResult

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fr_search_bus, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onSetView(this)
        setupRecyclerView()
        setupDataFlowToRecyclerAdapter()
    }

    private fun setupRecyclerView() {
        val resources = context.resources
        val recyclerColsCount = resources.getInteger(R.integer.searchResult_RecyclerColsCount)
        val grid = GridLayoutManager(context, recyclerColsCount)
        grid.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val integerViewType = when(adapter.getItemViewType(position)){
                    HEADER_VIEW_TYPE -> R.integer.searchResult_HeaderView
                    ROUTE_VIEW_TYPE -> R.integer.searchResult_RouteView
                    BUS_STOP_VIEW_TYPE -> R.integer.searchResult_BusStopView
                    else -> throw IllegalArgumentException("Returned unknown view type")
                }
                return resources.getInteger(integerViewType)
            }
        }
        recyclerView.layoutManager = grid

        val itemDecoration = SearchResultItemDecoration()
        recyclerView.addItemDecoration(itemDecoration)

        searchResultInterface = adapter
        recyclerView.adapter = adapter
    }

    // TODO
    private fun setupDataFlowToRecyclerAdapter() {
        val searchViewTextChangeListener = context as SearchView.OnSearchTextChangedListener
        val queryFlowable = searchViewTextChangeListener.getSearchTextChangeObservable()
        val searchResultFlowable = presenter.getSearchViewResult(queryFlowable)
        val searchResultDisposable = searchResultInterface.setData(searchResultFlowable)
        disposable.add(searchResultDisposable)
    }

    override fun showLoadingIndicator() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        noResultView.visibility = View.GONE
    }

    override fun hideLoadingIndicator() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        noResultView.visibility = View.GONE
    }

    override fun onEmptyResultFromServer() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
        noResultView.visibility = View.VISIBLE
    }

    override fun onUnknownErrorOnServerSide() {
        //TODO
    }

    override fun onNoInternetConnection() {
        //TODO
    }

    interface SearchResult {

        fun setData(newSearchResultData: Flowable<ArrayList<SearchResultData>>): Disposable
    }
}