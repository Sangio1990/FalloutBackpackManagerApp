package fallout.inventory.manager.data

data class Data(
    var strength : Int,
    var modifier: Int,
    val inventory: MutableList<Item>,
    val ammo: MutableList<Item>
)