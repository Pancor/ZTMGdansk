package pancor.pl.ztmgdansk.search_bus

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import pancor.pl.ztmgdansk.base.ActivityScope
import pancor.pl.ztmgdansk.data.BusDataManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ActivityScope
class SearchBusPresenter @Inject constructor(val view: SearchBusContract.View,
                                             val busDataManager: BusDataManager): SearchBusContract.Presenter{

    @Inject fun setupListeners() {
        view.setPresenter(this)
    }

    override fun setupSearchViewObservable(observable: Observable<String>) {
        observable
                .debounce(200, TimeUnit.MILLISECONDS)
                .filter { query -> query.length > 1 }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { query ->  }
    }
}