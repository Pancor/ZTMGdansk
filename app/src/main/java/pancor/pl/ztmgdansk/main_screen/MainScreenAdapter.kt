package pancor.pl.ztmgdansk.main_screen

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.holder_main_screen.view.*
import pancor.pl.ztmgdansk.R

class MainScreenAdapter(val items: List<RecyclerActionItem>,
                        val clickListener: OnRecyclerItemClickListener) :
        RecyclerView.Adapter<MainScreenAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val view = inflater.inflate(R.layout.holder_main_screen, parent, false)
        return ViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

    inner class ViewHolder(itemView: View, itemClick: OnRecyclerItemClickListener) :
            RecyclerView.ViewHolder(itemView) {

        var titleView: TextView = itemView.title
        var imageView: ImageView = itemView.image

        init{
            itemView.setOnClickListener {
                itemClick.onItemClick(adapterPosition, items[adapterPosition].viewType)
            }
        }

        fun bindView(item: RecyclerActionItem){
            titleView.text = item.title
            Glide.with(itemView.context)
                    .load(item.imageId)
                    .centerCrop()
                    .into(imageView)
        }
    }

    interface OnRecyclerItemClickListener{

        fun onItemClick(position: Int, itemType: Int)
    }
}