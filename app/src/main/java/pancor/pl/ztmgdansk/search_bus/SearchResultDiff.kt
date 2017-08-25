package pancor.pl.ztmgdansk.search_bus

import android.support.v7.util.DiffUtil
import pancor.pl.ztmgdansk.models.Route
import pancor.pl.ztmgdansk.models.SearchResultData


class SearchResultDiff(val oldList: List<SearchResultData>, val newList: List<SearchResultData>) :
        DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}