package pancor.pl.ztmgdansk.search_bus

import android.content.res.Resources
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.holder_bus_stop.view.*
import kotlinx.android.synthetic.main.holder_header.view.*
import kotlinx.android.synthetic.main.holder_route.view.*
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import pancor.pl.ztmgdansk.R
import pancor.pl.ztmgdansk.base.BUS_STOP_VIEW_TYPE
import pancor.pl.ztmgdansk.base.BaseViewHolder
import pancor.pl.ztmgdansk.base.HEADER_VIEW_TYPE
import pancor.pl.ztmgdansk.base.ROUTE_VIEW_TYPE
import pancor.pl.ztmgdansk.di.ActivityScope
import pancor.pl.ztmgdansk.models.*
import pancor.pl.ztmgdansk.tools.schedulers.BaseSchedulerProvider
import javax.inject.Inject

@ActivityScope
class SearchResultAdapter @Inject constructor(val schedulers: BaseSchedulerProvider) :
        RecyclerView.Adapter<BaseViewHolder>(), SearchBusFragment.SearchResult {

    private lateinit var resources: Resources
    private var searchResultData: List<SearchResultData> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        resources = parent.context.resources
        val inflater = LayoutInflater.from(parent.context)
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

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bindView(position)
    }

    override fun getItemCount(): Int {
        return searchResultData.size
    }

    override fun getItemViewType(position: Int): Int {
        return searchResultData[position].viewType
    }

    override fun setData(newSearchResultData: Flowable<ArrayList<SearchResultData>>): Disposable {
        var newResult = arrayListOf<SearchResultData>()
        return newSearchResultData
                .subscribeOn(schedulers.io())
                .doOnNext { newResult = it }
                .map{ DiffUtil.calculateDiff(SearchResultDiff(searchResultData, it), false) }
                .observeOn(schedulers.ui())
                .doOnNext { searchResultData = newResult }
                .subscribe { it.dispatchUpdatesTo(this) }
    }

    inner class HeaderHolder(itemView: View) : BaseViewHolder(itemView) {

        override fun bindView(position: Int) {
            val header = searchResultData[position].model as Header
            itemView.headerTitle.text = resources.getString(header.title)
        }
    }

    inner class RouteHolder(itemView: View) : BaseViewHolder(itemView) {

        override fun bindView(position: Int) {
            val route = searchResultData[position].model as Route
            itemView.routeShortName.text = route.routeShortName
        }
    }

    inner class BusStopHolder(itemView: View) : BaseViewHolder(itemView) {

        override fun bindView(position: Int) {
            val busStop = searchResultData[position].model as BusStop
            itemView.busStopName.text = busStop.stopName
        }
    }
}