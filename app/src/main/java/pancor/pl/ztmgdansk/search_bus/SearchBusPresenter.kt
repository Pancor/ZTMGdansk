package pancor.pl.ztmgdansk.search_bus

import pancor.pl.ztmgdansk.base.ActivityScope
import pancor.pl.ztmgdansk.data.BusDataManager
import javax.inject.Inject

@ActivityScope
class SearchBusPresenter @Inject constructor(val view: SearchBusContract.View,
                                             val busDataManager: BusDataManager): SearchBusContract.Presenter{

    @Inject fun setupListeners() {
        view.setPresenter(this)
    }
}