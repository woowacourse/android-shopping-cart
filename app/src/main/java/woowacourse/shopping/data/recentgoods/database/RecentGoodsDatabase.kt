package woowacourse.shopping.data.recentgoods.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RecentGoodsEntity::class], version = 1)
abstract class RecentGoodsDatabase : RoomDatabase() {
    abstract fun recentGoodsDao(): RecentGoodsDao

    companion object {
        @Volatile
        private var INSTANCE: RecentGoodsDatabase? = null

        fun getDatabase(context: Context): RecentGoodsDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    RecentGoodsDatabase::class.java,
                    "recent_goods",
                ).build().also { INSTANCE = it }
            }
        }
    }
}
