package fallout.inventory.manager.data

data class Data(
    val strong : Int,
    val modifier: Int,
    val inventory: MutableList<Item>,
    val ammo: MutableList<Item>
)