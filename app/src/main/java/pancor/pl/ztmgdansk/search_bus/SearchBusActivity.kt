package pancor.pl.ztmgdansk.search_bus

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.act_search_bus.*
import pancor.pl.ztmgdansk.R
import pancor.pl.ztmgdansk.R.layout.act_search_bus
import pancor.pl.ztmgdansk.base.App
import pancor.pl.ztmgdansk.tools.OtherUtils
import pancor.pl.ztmgdansk.tools.CustomSearchView.OnBackNavigationClickListener
import pancor.pl.ztmgdansk.tools.schedulers.SchedulerModule
import javax.inject.Inject

class SearchBusActivity : AppCompatActivity(), OnBackNavigationClickListener {

    @Inject lateinit var presenter: SearchBusPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(act_search_bus)

        setupFragment()
        setupSearchView()
    }

    override fun onPause() {
        super.onPause()
        presenter.onStop()
    }

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
        presenter.setupSearchViewObservable(searchView.getTextChangeObservable())
    }
}