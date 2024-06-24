package fallout.inventory.manager.data

object DataUtility {
    val TAG = "FalloutManger"
    lateinit private var selectedView: String
    lateinit var data: Data

    fun dataInit() {
        val temp = GsonUtility.loadData()
        if (temp != null) {
            data = temp
        }
    }

    fun getSelectedView(): String = selectedView

    fun setSelectedView(view: String) {
        selectedView = view
    }

    fun saveData() {
        GsonUtility.saveData(data)
    }

    fun loadItems(): List<Item> {
        return data.inventory
    }

    fun loadAmmo(): List<Item> {
        return data.ammo
    }

    fun getSTR(): Int {
        return data.strength
    }

    fun setStrength(str: Int) {
        data.strength = str
        saveData()
    }

    fun getModifier(): Int {
        return data.modifier
    }

    fun setModifier(mod: Int) {
        data.modifier = mod
        saveData()
    }

    fun addItem(item: Item) {
        data.inventory.add(item)
        saveData()
    }

    fun addNewAmmo(item: Item) {
        data.ammo.add(item)
        saveData()
    }

    fun removeItem(item: Item) {
        data.inventory.remove(item)
        saveData()
    }

    fun removeAmmo(item: Item) {
        data.ammo.remove(item)
        saveData()
    }

    fun getTotalWeight(): Double {
        var totalWeight = 0.0
        for (item in data.inventory)
            totalWeight += item.weight * item.quantity
        return totalWeight
    }

    fun getMaxWeight(): Int {
        return data.strength * 5 + 75 + data.modifier
    }

    fun changeAmmoQuantity(itemaa: Item, qnt: Int) {
        data.ammo.find { itemaa.name == it.name }
            ?.let {
                it.quantity += qnt
            }
        saveData()
    }
}