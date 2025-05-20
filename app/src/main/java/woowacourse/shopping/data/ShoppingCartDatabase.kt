package woowacourse.shopping.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.data.product.ProductEntity
import woowacourse.shopping.data.shoppingcart.ShoppingCartDao
import woowacourse.shopping.data.shoppingcart.ShoppingCartEntity

@Database(entities = [ProductEntity::class, ShoppingCartEntity::class], version = 1)
abstract class ShoppingCartDatabase : RoomDatabase() {
    abstract fun shoppingCartDao(): ShoppingCartDao

    companion object {
        @Volatile
        private var instance: ShoppingCartDatabase? = null

        fun getDataBase(context: Context): ShoppingCartDatabase =
            instance ?: synchronized(this) {
                val instance =
                    Room
                        .databaseBuilder(
                            context.applicationContext,
                            ShoppingCartDatabase::class.java,
                            "product_database",
                        ).fallbackToDestructiveMigration(true)
                        .build()
                Companion.instance = instance

                instance
            }
    }
}
