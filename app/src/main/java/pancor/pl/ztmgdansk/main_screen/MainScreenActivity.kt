package pancor.pl.ztmgdansk.main_screen

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import kotlinx.android.synthetic.main.act_main_screen.*
import pancor.pl.ztmgdansk.R
import pancor.pl.ztmgdansk.R.layout.act_main_screen
import pancor.pl.ztmgdansk.tools.OtherUtils
import pancor.pl.ztmgdansk.tools.ui.CustomDividerItemDecoration

class MainScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(act_main_screen)

        setupFragment()
    }

    private fun setupFragment(){
        var mainScreenFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.container)
        if (mainScreenFragment == null){
            mainScreenFragment = MainScreenFragment()
            OtherUtils().addFragmentToActivity(supportFragmentManager,
                    mainScreenFragment, R.id.container)
        }
    }
}
