package pancor.pl.ztmgdansk.tools.schedulers

import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class SchedulerModule {

    @Binds
    @Singleton
    abstract fun provideScheduler(schedulerProvider: SchedulerProvider): BaseSchedulerProvider
}