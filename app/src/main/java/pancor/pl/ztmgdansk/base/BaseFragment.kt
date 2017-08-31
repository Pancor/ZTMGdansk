package pancor.pl.ztmgdansk.base

import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable


abstract class BaseFragment : DaggerFragment() {

    protected val disposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}