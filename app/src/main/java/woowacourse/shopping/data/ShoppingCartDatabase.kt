package woowacourse.shopping.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductEntity::class, ShoppingCartEntity::class], version = 2)
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
                        ).build()
                Companion.instance = instance

                instance
            }
    }
}
