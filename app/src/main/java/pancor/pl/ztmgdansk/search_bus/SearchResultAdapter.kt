package pancor.pl.ztmgdansk.search_bus

import android.content.res.Resources
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.holder_bus_stop.view.*
import kotlinx.android.synthetic.main.holder_header.view.*
import kotlinx.android.synthetic.main.holder_main_screen.view.*
import kotlinx.android.synthetic.main.holder_route.view.*
import pancor.pl.ztmgdansk.R
import pancor.pl.ztmgdansk.base.BUS_STOP_VIEW_TYPE
import pancor.pl.ztmgdansk.base.HEADER_VIEW_TYPE
import pancor.pl.ztmgdansk.base.ROUTE_VIEW_TYPE
import pancor.pl.ztmgdansk.models.*
import pancor.pl.ztmgdansk.tools.schedulers.BaseSchedulerProvider

class SearchResultAdapter(val resources: Resources, val schedulers: BaseSchedulerProvider) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
        SearchBusFragment.SearchResult{

    var searchResultData: List<SearchResultData> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        when (viewType) {
            HEADER_VIEW_TYPE -> {
                val headerView = inflater.inflate(R.layout.holder_header, parent, false)
                return HeaderHolder(headerView)
            }
            ROUTE_VIEW_TYPE -> {
                val routeView = inflater.inflate(R.layout.holder_route, parent, false)
                return RouteHolder(routeView)
            }
            BUS_STOP_VIEW_TYPE -> {
                val busStopView = inflater.inflate(R.layout.holder_bus_stop, parent, false)
                return BusStopHolder(busStopView)
            }
            else -> throw IllegalArgumentException("viewType returned unexpected type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when (getItemViewType(position)) {
            HEADER_VIEW_TYPE -> {
                val headerHolder = holder as HeaderHolder
                headerHolder.bindView(position)
            }
            ROUTE_VIEW_TYPE -> {
                val routeHolder = holder as RouteHolder
                routeHolder.bindView(position)
            }
            BUS_STOP_VIEW_TYPE -> {
                val busStopHolder = holder as BusStopHolder
                busStopHolder.bindView(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return searchResultData.size
    }

    override fun getItemViewType(position: Int): Int {
        return searchResultData[position].viewType
    }

    override fun setData(newSearchResultData: List<SearchResultData>): Disposable {
        return Flowable.just(newSearchResultData)
                .subscribeOn(schedulers.computation())
                .map{ DiffUtil.calculateDiff(SearchResultDiff(searchResultData, it)) }
                .observeOn(schedulers.ui())
                .doOnNext { searchResultData = newSearchResultData }
                .subscribe { it.dispatchUpdatesTo(this) }
    }

    inner class HeaderHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindView(position: Int) {
            val header = searchResultData[position].model as Header
            itemView.headerTitle.text = resources.getString(header.title)
        }
    }

    inner class RouteHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindView(position: Int) {
            val route = searchResultData[position].model as Route
            itemView.routeShortName.text = route.routeShortName
            itemView.busType.text = "Autobus"
        }
    }

    inner class BusStopHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindView(position: Int) {
            val busStop = searchResultData[position].model as BusStop
            itemView.busStopName.text = busStop.stopName
        }
    }
}