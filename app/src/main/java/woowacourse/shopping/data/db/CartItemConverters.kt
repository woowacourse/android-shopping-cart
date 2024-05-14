package woowacourse.shopping.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import woowacourse.shopping.domain.Product

class CartItemConverters {
    private val gson = Gson()
    @TypeConverter
    fun fromProduct(product: Product): String {
        return gson.toJson(product)
    }

    @TypeConverter
    fun toProduct(product: String): Product {
        return gson.fromJson(product, Product::class.java)
    }

}
