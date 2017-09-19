package pancor.pl.ztmgdansk.models

data class Header constructor(override val id: Int) : SearchResultData.Model() {

    companion object {
        const val ROUTE = 20
        const val BUS_STOP = 21
    }
}

