package pancor.pl.ztmgdansk.main_screen

object RecyclerActionItemCons{
    val TIMETABLE: Int = 100
}

data class RecyclerActionItem(val viewType: Int = -1, val title: String, val imageId: Int)