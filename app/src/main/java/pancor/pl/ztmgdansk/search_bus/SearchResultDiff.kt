package pancor.pl.ztmgdansk.search_bus

import android.support.v7.util.DiffUtil
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Header
import pancor.pl.ztmgdansk.models.Route
import pancor.pl.ztmgdansk.models.SearchResultData

class SearchResultDiff(private val oldList: List<SearchResultData>,
                       private val newList: List<SearchResultData>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].viewType == newList[newItemPosition].viewType &&
        oldList[oldItemPosition].model.id == newList[newItemPosition].model.id

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        when (oldList[oldItemPosition].model) {
            is Header -> return isHeaderContentTheSame(oldItemPosition, newItemPosition)
            is Route -> return isRouteTheSame(oldItemPosition, newItemPosition)
            is BusStop -> return isBusStopTheSame(oldItemPosition, newItemPosition)
            else -> throw IllegalArgumentException("Unexpected model type")
        }
    }

    private fun isHeaderContentTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldHeader = oldList[oldItemPosition].model as Header
        val newHeader = newList[newItemPosition].model as Header
        return oldHeader == newHeader
    }

    private fun isRouteTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldRoute = oldList[oldItemPosition].model as Route
        val newRoute = newList[newItemPosition].model as Route
        return oldRoute == newRoute
    }

    private fun isBusStopTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldBusStop = oldList[oldItemPosition].model as BusStop
        val newBusStop = newList[newItemPosition].model as BusStop
        return oldBusStop == newBusStop
    }
}