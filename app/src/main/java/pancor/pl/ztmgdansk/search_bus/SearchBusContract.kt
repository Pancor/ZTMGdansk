package pancor.pl.ztmgdansk.search_bus

import io.reactivex.Flowable
import io.reactivex.Observable
import pancor.pl.ztmgdansk.base.BasePresenter
import pancor.pl.ztmgdansk.base.BaseView
import pancor.pl.ztmgdansk.models.SearchResultData

interface SearchBusContract {

    interface View : BaseView {

        fun onEmptyResultFromServer()

        fun onUnknownErrorOnServerSide()

        fun onNoInternetConnection()
    }

    interface Presenter : BasePresenter<View> {

        fun getSearchViewResult(searchViewObservable: Flowable<String>): Flowable<ArrayList<SearchResultData>>
    }
}