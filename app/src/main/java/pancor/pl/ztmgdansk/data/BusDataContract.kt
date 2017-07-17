package pancor.pl.ztmgdansk.data

interface BusDataContract {

    interface LoadRoutesAndStopsCallback {

        fun onRoutesAndStopsLoaded()

        fun onDataNotAvailable()
    }

    fun getRoutesAndStops(callback: LoadRoutesAndStopsCallback)
}