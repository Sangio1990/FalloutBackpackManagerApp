package fallout.inventory.manager.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fallout.inventory.manager.data.DataUtility.TAG
import java.io.File

object GsonUtility {
    lateinit var file: File

    fun gsonInit(context: Context) {
        file = File(context.getExternalFilesDir("FalloutManager"), "inventory.json")
        if (!file.exists()) {
            file.createNewFile()
            saveData(Data(0, 0, mutableListOf(), mutableListOf()))
        }
    }

    fun loadData(): Data? {
        val json = file.readText()
        val type = object : TypeToken<Data>() {}.type
        try {
            return Gson().fromJson(json, type)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load data from JSON file: ${e.message}")
            throw Exception("JSON corrotto o non Trovato")
        }
    }

    fun saveData(data: Data) {
        val json = Gson().toJson(data)
        file.writeText(json)
    }
}