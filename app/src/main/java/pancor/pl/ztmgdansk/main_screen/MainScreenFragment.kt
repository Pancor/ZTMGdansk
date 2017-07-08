package pancor.pl.ztmgdansk.main_screen

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fr_main_screen.*
import pancor.pl.ztmgdansk.R
import pancor.pl.ztmgdansk.R.layout.*
import pancor.pl.ztmgdansk.tools.OtherUtils
import pancor.pl.ztmgdansk.tools.ui.CustomDividerItemDecoration

class MainScreenFragment : Fragment(), MainScreenAdapter.OnRecyclerItemClickListener {

    val RECYCLER_ITEM_DIVIDER_IN_DP = 8

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(fr_main_screen, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    override fun onItemClick(position: Int, itemType: Int) {
        Toast.makeText(context, "Position: $position, itemType: $itemType", Toast.LENGTH_LONG).show()
    }

    fun setupRecyclerView(){
        recyclerView.setHasFixedSize(true)

        val grid = GridLayoutManager(context, 1)
        recyclerView.layoutManager = grid
        val list = getRecyclerViewList()

        val space = OtherUtils().getPixelsFromDP(context, RECYCLER_ITEM_DIVIDER_IN_DP)
        val decorator: RecyclerView.ItemDecoration = CustomDividerItemDecoration(space)
        recyclerView.addItemDecoration(decorator)

        val adapter = MainScreenAdapter(list, this)
        recyclerView.adapter = adapter
    }

    fun getRecyclerViewList(): List<RecyclerActionItem>{
        val list = ArrayList<RecyclerActionItem>()
        list.add(RecyclerActionItem(RecyclerActionItemCons.TIMETABLE,
                "Rozkład jazdyRozkład jazdyRozkład jazdyRozkład jazdyRozkład jazdyRozkład " +
                        "jazdyRozkład jazdyRozkład jazdyRozkład jazdyRozkład jazdy", R.drawable.img_autobus))
        list.add(RecyclerActionItem(RecyclerActionItemCons.TIMETABLE,
                "Rozkład jazdy", R.drawable.img_autobus))
        list.add(RecyclerActionItem(RecyclerActionItemCons.TIMETABLE,
                "Rozkład jazdy", R.drawable.img_autobus))
        list.add(RecyclerActionItem(RecyclerActionItemCons.TIMETABLE,
                "Rozkład jazdy", R.drawable.img_autobus))
        list.add(RecyclerActionItem(RecyclerActionItemCons.TIMETABLE,
                "Rozkład jazdy", R.drawable.img_autobus))
        list.add(RecyclerActionItem(RecyclerActionItemCons.TIMETABLE,
                "Rozkład jazdy", R.drawable.img_autobus))
        return list
    }
}