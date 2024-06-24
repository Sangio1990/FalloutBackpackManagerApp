package fallout.inventory.manager.data

import androidx.lifecycle.ViewModel

object ItemViewModel : ViewModel() {
    var selectedItem: Item? = null
    var isItem: Boolean = true
}

