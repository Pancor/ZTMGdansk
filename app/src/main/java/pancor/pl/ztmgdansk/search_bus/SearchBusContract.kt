package pancor.pl.ztmgdansk.search_bus

import io.reactivex.Observable
import pancor.pl.ztmgdansk.base.BaseView
import pancor.pl.ztmgdansk.models.SearchResultData

interface SearchBusContract {

    interface View : BaseView<Presenter> {

        fun onSearchResult(searchResultData: List<SearchResultData>)
    }

    interface Presenter {

        fun onStop()

        fun setupSearchViewObservable(observable: Observable<String>)
    }
}