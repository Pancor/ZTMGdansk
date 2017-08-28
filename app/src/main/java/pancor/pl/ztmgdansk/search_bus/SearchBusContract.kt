package pancor.pl.ztmgdansk.search_bus

import io.reactivex.Flowable
import io.reactivex.Observable
import pancor.pl.ztmgdansk.base.BaseView
import pancor.pl.ztmgdansk.models.SearchResultData

interface SearchBusContract {

    interface View : BaseView<Presenter> {

        fun onSearchResult(searchResultData: Flowable<List<SearchResultData>>)
    }

    interface Presenter {

        fun onStop()

        fun getSearchViewResult(searchViewObservable: Flowable<String>): Flowable<ArrayList<SearchResultData>>
    }
}