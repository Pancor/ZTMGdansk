package pancor.pl.ztmgdansk.search_bus

import android.os.Bundle
import dagger.Lazy
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Flowable
import kotlinx.android.synthetic.main.act_search_bus.*
import pancor.pl.ztmgdansk.R
import pancor.pl.ztmgdansk.R.layout.act_search_bus
import pancor.pl.ztmgdansk.tools.OtherUtils
import pancor.pl.ztmgdansk.tools.search.SearchView
import javax.inject.Inject

class SearchBusActivity : DaggerAppCompatActivity(), SearchView.OnBackArrowClickListener,
        SearchView.OnSearchTextChangedListener {

    @Inject
    lateinit var searchBusFragmentProvider: Lazy<SearchBusFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(act_search_bus)

        setupFragment()
        setupSearchView()
    }

    override fun getSearchTextChangeObservable() = searchView.getTextChangeObservable()

    override fun onBackArrowClick() {
        onBackPressed()
    }

    private fun setupSearchView() {
        searchView.setOnBackArrowClickListener(this)
    }

    private fun setupFragment(){
        var searchBusFragment: SearchBusFragment? = supportFragmentManager
                .findFragmentById(R.id.container) as SearchBusFragment?
        if (searchBusFragment == null){
            searchBusFragment = searchBusFragmentProvider.get()
            OtherUtils().addFragmentToActivity(supportFragmentManager,
                    searchBusFragment, R.id.container)
        }
    }
}