package pancor.pl.ztmgdansk.search_bus

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import pancor.pl.ztmgdansk.models.BusStop
import pancor.pl.ztmgdansk.models.Route


class SearchResultAdapter(val routes: List<Route>,
                          val stops: List<BusStop>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    inner class HeaderHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }

    inner class RouteHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }

    inner class BusStopHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }
}