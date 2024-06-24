package fallout.inventory.manager.data

data class Item(
    val name: String,
    val description: String,
    var quantity: Int,
    val weight: Double,
    val value: Int,
)