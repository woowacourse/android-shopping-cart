package woowacourse.shopping.data.recent

import androidx.room.TypeConverter
import com.google.gson.Gson
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct

class RecentProductConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromProduct(product: Product): String {
        return gson.toJson(product)
    }

    @TypeConverter
    fun toProduct(productString: String): Product {
        return gson.fromJson(productString, Product::class.java)
    }
}
