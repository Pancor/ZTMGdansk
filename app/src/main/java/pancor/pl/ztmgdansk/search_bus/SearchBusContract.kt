package pancor.pl.ztmgdansk.search_bus

import io.reactivex.Observable
import pancor.pl.ztmgdansk.base.BaseView


interface SearchBusContract {

    interface View : BaseView<Presenter> {

    }

    interface Presenter {

        fun setupSearchViewObservable(observable: Observable<String>)
    }
}