package woowacourse.shopping.data.recentproduct

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [RecentProduct::class], version = 1)
@TypeConverters(RecentProductTypeConverters::class)
abstract class RecentProductDatabase : RoomDatabase() {
    abstract fun recentProductDao(): RecentProductDao

    companion object {
        private const val DATABASE_NAME = "recent_product_db"
        private const val ERROR_DATABASE = "데이터베이스가 초기화 되지 않았습니다."
        private var database: RecentProductDatabase? = null

        fun database(): RecentProductDatabase {
            return database ?: throw IllegalStateException(ERROR_DATABASE)
        }

        fun initialize(context: Context) {
            if (database == null) {
                database =
                    Room.databaseBuilder(
                        context.applicationContext,
                        RecentProductDatabase::class.java,
                        DATABASE_NAME,
                    ).build()
            }
        }
    }
}
