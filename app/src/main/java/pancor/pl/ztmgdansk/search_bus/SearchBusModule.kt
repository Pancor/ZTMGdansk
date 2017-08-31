package pancor.pl.ztmgdansk.search_bus

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import pancor.pl.ztmgdansk.di.ActivityScope
import pancor.pl.ztmgdansk.di.FragmentScope

@Module
abstract class SearchBusModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun searchBusFragmnet(): SearchBusFragment

    @ActivityScope
    @Binds
    abstract fun searchBusPresenter(presenter: SearchBusPresenter): SearchBusContract.Presenter
}