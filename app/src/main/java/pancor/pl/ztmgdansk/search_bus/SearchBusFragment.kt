package pancor.pl.ztmgdansk.search_bus

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fr_search_bus.*
import pancor.pl.ztmgdansk.R
import pancor.pl.ztmgdansk.base.HEADER_VIEW_TYPE
import pancor.pl.ztmgdansk.models.SearchResultData
import pancor.pl.ztmgdansk.tools.CustomSearchView
import pancor.pl.ztmgdansk.tools.SearchResultItemDecoration
import pancor.pl.ztmgdansk.tools.schedulers.SchedulerProvider
import javax.inject.Inject

class SearchBusFragment @Inject constructor(): DaggerFragment(), SearchBusContract.View {

    @Inject
    lateinit var presenter: SearchBusContract.Presenter
    private val disposable = CompositeDisposable()
    private lateinit var searchResultInterface: SearchResult

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fr_search_bus, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onSetView(this)
        setupRecyclerView()
        setupSearchViewListener()
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

        //TODO make it inject scheduler provider and resources
        val adapter = SearchResultAdapter(resources, SchedulerProvider())
        searchResultInterface = adapter
        recyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    override fun onSearchResult(searchResultData: Flowable<List<SearchResultData>>) {

    }

    override fun showLoadingIndicator() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    override fun hideLoadingIndicator() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    private fun setupSearchViewListener() {
        val searchViewTextChangeListener = context as CustomSearchView.SearchViewTextChangeListener
        searchResultInterface.setData(presenter.getSearchViewResult(searchViewTextChangeListener.getSearchViewTextChangeListener()))
    }

    interface SearchResult {

        fun setData(newSearchResultData: Flowable<ArrayList<SearchResultData>>): Disposable
    }
}