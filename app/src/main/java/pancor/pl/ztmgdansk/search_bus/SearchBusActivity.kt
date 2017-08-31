package pancor.pl.ztmgdansk.search_bus

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import dagger.Lazy
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import kotlinx.android.synthetic.main.act_search_bus.*
import pancor.pl.ztmgdansk.R
import pancor.pl.ztmgdansk.R.layout.act_search_bus
import pancor.pl.ztmgdansk.tools.CustomSearchView
import pancor.pl.ztmgdansk.tools.OtherUtils
import pancor.pl.ztmgdansk.tools.CustomSearchView.OnBackNavigationClickListener
import javax.inject.Inject

class SearchBusActivity : DaggerAppCompatActivity(), OnBackNavigationClickListener, CustomSearchView.SearchViewTextChangeListener {

    @Inject
    lateinit var presenter: SearchBusPresenter
    @Inject
    lateinit var searchBusFragmentProvider: Lazy<SearchBusFragment>

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
            searchBusFragment = searchBusFragmentProvider.get()
            OtherUtils().addFragmentToActivity(supportFragmentManager,
                    searchBusFragment, R.id.container)
        }
    }

    private fun setupSearchView() {
        searchView.setBackNavigationListener(this)
    }
}