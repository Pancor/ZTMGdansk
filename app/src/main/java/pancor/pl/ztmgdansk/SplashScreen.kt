package pancor.pl.ztmgdansk

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import pancor.pl.ztmgdansk.main_screen.MainScreenActivity

class SplashScreen : AppCompatActivity() {

    infix fun Int.with(x: Int) = this.or(x)

    override fun onCreate(state: Bundle?){
        super.onCreate(state)

        val intent = Intent(this, MainScreenActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP with
                        Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}