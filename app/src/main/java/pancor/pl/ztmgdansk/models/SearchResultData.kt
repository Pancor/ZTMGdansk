package pancor.pl.ztmgdansk.models


data class SearchResultData(val model: Model, val viewType: Int) {

    init {
        if (model !is Model) {
            throw ClassCastException("Model parameter should extend SearchResultData.Model class")
        }
    }

    abstract class Model {
        abstract val id: Int
    }
}
