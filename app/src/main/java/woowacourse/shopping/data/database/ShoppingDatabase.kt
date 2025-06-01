package woowacourse.shopping.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.data.dao.CartProductDao
import woowacourse.shopping.data.dao.RecentlyViewedProductDao
import woowacourse.shopping.data.entity.CartProductEntity
import woowacourse.shopping.data.entity.RecentlyViewedProductEntity

@Database(entities = [CartProductEntity::class, RecentlyViewedProductEntity::class], version = 1)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun cartProductDao(): CartProductDao

    abstract fun recentlyViewedProductDao(): RecentlyViewedProductDao

    companion object {
        @Volatile
        private var iNSTANCE: ShoppingDatabase? = null

        fun getInstance(context: Context): ShoppingDatabase =
            iNSTANCE ?: synchronized(this) {
                val instance =
                    Room
                        .databaseBuilder(
                            context.applicationContext,
                            ShoppingDatabase::class.java,
                            "app_database",
                        ).fallbackToDestructiveMigration()
                        .build()

                iNSTANCE = instance
                instance
            }
    }
}
