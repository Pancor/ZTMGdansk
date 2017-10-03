package pancor.pl.ztmgdansk.search_bus

import android.os.Bundle
import dagger.Lazy
import dagger.android.support.DaggerAppCompatActivity
import pancor.pl.ztmgdansk.R
import pancor.pl.ztmgdansk.R.layout.act_search_bus
import pancor.pl.ztmgdansk.tools.OtherUtils
import javax.inject.Inject

class SearchBusActivity : DaggerAppCompatActivity(){

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

    private fun setupSearchView() {


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