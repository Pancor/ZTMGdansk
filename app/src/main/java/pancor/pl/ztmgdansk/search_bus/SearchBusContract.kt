package pancor.pl.ztmgdansk.search_bus

import io.reactivex.Observable
import pancor.pl.ztmgdansk.base.BaseView
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Route


interface SearchBusContract {

    interface View : BaseView<Presenter> {

        fun onSearchResult(routes: List<Route>, stops: List<BusStop>)
    }

    interface Presenter {

        fun setupSearchViewObservable(observable: Observable<String>)
    }
}