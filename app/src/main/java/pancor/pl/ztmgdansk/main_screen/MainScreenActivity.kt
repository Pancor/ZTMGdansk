package pancor.pl.ztmgdansk.main_screen

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import kotlinx.android.synthetic.main.act_main_screen.*
import pancor.pl.ztmgdansk.R
import pancor.pl.ztmgdansk.tools.OtherUtils
import pancor.pl.ztmgdansk.tools.ui.CustomDividerItemDecoration

class MainScreenActivity : AppCompatActivity(), MainScreenAdapter.OnRecyclerItemClickListener {

    val RECYCLER_ITEM_DIVIDER_IN_DP = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main_screen)

        setupRecyclerView()
    }

    override fun onItemClick(position: Int, itemType: Int) {
        Toast.makeText(this, "Position: $position, itemType: $itemType", Toast.LENGTH_LONG).show()
    }

    fun setupRecyclerView(){
        recyclerView.setHasFixedSize(true)

        val grid = GridLayoutManager(this, 1)
        recyclerView.layoutManager = grid
        val list = getRecyclerViewList()

        val space = OtherUtils().getPixelsFromDP(this, RECYCLER_ITEM_DIVIDER_IN_DP)
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
