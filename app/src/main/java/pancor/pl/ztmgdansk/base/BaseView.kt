package pancor.pl.ztmgdansk.base


interface BaseView<T> {

    fun setPresenter(presenter: T)

    fun showLoadingIndicator()

    fun hideLoadingIndicator()
}