package pancor.pl.ztmgdansk.search_bus

import dagger.BindsInstance
import dagger.Component
import pancor.pl.ztmgdansk.base.ActivityScope
import pancor.pl.ztmgdansk.data.BusDataComponent

@ActivityScope
@Component(dependencies = arrayOf(BusDataComponent::class))
interface SearchBusComponent {

    fun inject(activity: SearchBusActivity)

    @Component.Builder
    interface Builder {

        fun build(): SearchBusComponent
        @BindsInstance fun view(v: SearchBusContract.View): Builder
        fun dataManager(manager: BusDataComponent): Builder
    }
}