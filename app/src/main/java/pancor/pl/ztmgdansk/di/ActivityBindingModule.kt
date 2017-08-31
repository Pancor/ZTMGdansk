package pancor.pl.ztmgdansk.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pancor.pl.ztmgdansk.search_bus.SearchBusActivity
import pancor.pl.ztmgdansk.search_bus.SearchBusModule

@Module
abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(SearchBusModule::class))
    abstract fun bindSearchBusActivity(): SearchBusActivity
}