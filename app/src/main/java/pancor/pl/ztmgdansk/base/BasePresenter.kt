package pancor.pl.ztmgdansk.base


interface BasePresenter<T> {

    fun onSetView(view: T)
}