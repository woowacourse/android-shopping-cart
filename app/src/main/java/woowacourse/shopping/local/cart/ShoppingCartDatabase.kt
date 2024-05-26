package woowacourse.shopping.local.cart

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.data.model.ProductIdsCountData

@Database(entities = [ProductIdsCountData::class], version = 1)
abstract class ShoppingCartDatabase : RoomDatabase() {
    abstract fun dao(): ShoppingCartDao

    companion object {
        @Volatile
        private var instance: ShoppingCartDatabase? = null

        fun database(context: Context): ShoppingCartDatabase =
            instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context, ShoppingCartDatabase::class.java, "shopping_cart_database",
                ).build().also { instance = it }
            }
    }
}
