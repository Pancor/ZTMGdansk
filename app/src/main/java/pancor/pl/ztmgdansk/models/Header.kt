package pancor.pl.ztmgdansk.models

data class Header constructor(override val id: Int, val title: Int) : SearchResultData.Model() {

    constructor(title: Int): this(title, title)
}

