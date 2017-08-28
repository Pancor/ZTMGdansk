package pancor.pl.ztmgdansk.search_bus

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import kotlinx.android.synthetic.main.act_search_bus.*
import pancor.pl.ztmgdansk.R
import pancor.pl.ztmgdansk.R.layout.act_search_bus
import pancor.pl.ztmgdansk.base.App
import pancor.pl.ztmgdansk.tools.CustomSearchView
import pancor.pl.ztmgdansk.tools.OtherUtils
import pancor.pl.ztmgdansk.tools.CustomSearchView.OnBackNavigationClickListener
import javax.inject.Inject

class SearchBusActivity : AppCompatActivity(), OnBackNavigationClickListener, CustomSearchView.SearchViewTextChangeListener {

    @Inject lateinit var presenter: SearchBusPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(act_search_bus)

        setupFragment()
        setupSearchView()
    }

    override fun getSearchViewTextChangeListener(): Flowable<String> =
            searchView.getTextChangeObservable().toFlowable(BackpressureStrategy.BUFFER)

    override fun onBackNavigationClickListener() {
        onBackPressed()
    }

    private fun setupFragment(){
        var searchBusFragment: SearchBusFragment? = supportFragmentManager
                .findFragmentById(R.id.container) as SearchBusFragment?
        if (searchBusFragment == null){
            searchBusFragment = SearchBusFragment()
            OtherUtils().addFragmentToActivity(supportFragmentManager,
                    searchBusFragment, R.id.container)
        }
        setupInjection(searchBusFragment)
    }

    private fun setupInjection(view: SearchBusFragment) {
        DaggerSearchBusComponent.builder()
                .dataManager((application as App).busDataComponentBuild)
                .view(view)
                .build().inject(this)
    }

    private fun setupSearchView() {
        searchView.setBackNavigationListener(this)
    }
}