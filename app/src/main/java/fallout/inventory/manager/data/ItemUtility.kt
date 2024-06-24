package fallout.inventory.manager.data

class ItemParser {

}

class ListTestData{
    fun fakeData(): List<Item>{
        val mockList = mutableListOf<Item>()
        for (i in 0..10)
            mockList.add(Item("Item con un nome molto lungo per testare il comportamento ${i}", "Description ${i}", i, i*0.5, i*10))
        return mockList
    }
}