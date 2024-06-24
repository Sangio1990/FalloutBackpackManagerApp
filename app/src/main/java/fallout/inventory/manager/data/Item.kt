package fallout.inventory.manager.data

data class Item(
    val name: String,
    val description: String,
    val quantity: Int,
    val weight: Double,
    val value: Int,
)