package pancor.pl.ztmgdansk.search_bus

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.act_search_bus.*
import pancor.pl.ztmgdansk.R
import pancor.pl.ztmgdansk.R.layout.act_search_bus
import pancor.pl.ztmgdansk.tools.OtherUtils
import pancor.pl.ztmgdansk.tools.CustomSearchView.OnBackNavigationClickListener

class SearchBusActivity : AppCompatActivity(), OnBackNavigationClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(act_search_bus)

        setupFragment()
        setupSearchView()
    }

    override fun onBackNavigationClickListener() {
        onBackPressed()
    }

    private fun setupFragment(){
        var searchBusFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.container)
        if (searchBusFragment == null){
            searchBusFragment = SearchBusFragment()
            OtherUtils().addFragmentToActivity(supportFragmentManager,
                    searchBusFragment, R.id.container)
        }
    }

    private fun setupSearchView() {
        searchView.setBackNavigationListener(this)
        searchView.getTextChangeObservable()
                .subscribe { text -> Toast.makeText(this, text, Toast.LENGTH_LONG).show() }
    }
}