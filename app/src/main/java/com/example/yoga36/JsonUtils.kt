import android.content.Context
import com.example.yoga36.YogaPose
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

object JsonUtils {
    fun getYogaPoses(context: Context): List<YogaPose> {
        val jsonString = try {
            context.assets.open("yoga_poses.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return emptyList()
        }

        val listType = object : TypeToken<List<YogaPose>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }
}