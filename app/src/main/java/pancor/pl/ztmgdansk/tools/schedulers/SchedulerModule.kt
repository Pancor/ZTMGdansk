package pancor.pl.ztmgdansk.tools.schedulers

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SchedulerModule {

    @Provides
    @Singleton
    fun provideScheduler(): BaseSchedulerProvider {
        return SchedulerProvider()
    }
}