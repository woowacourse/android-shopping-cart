package woowacourse.shopping.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import woowacourse.shopping.data.db.dao.HistoryDao
import woowacourse.shopping.data.db.dao.ProductDao
import woowacourse.shopping.data.db.model.HistoryEntity
import woowacourse.shopping.data.db.model.ProductEntity

@Database(
    version = 1,
    entities = [
        ProductEntity::class,
        HistoryEntity::class,
    ],
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var dbInstance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return dbInstance ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "shopping_database",
                    ).addCallback(
                        object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onOpen(db)
                                initDb(context)
                            }
                        },
                    ).fallbackToDestructiveMigration().build()
                dbInstance = instance
                instance
            }
        }

        fun initDb(context: Context) {
            val db = getDatabase(context)
            Thread {
                /*
                with(db.movieDao()) {
                    insert(Movie.STUB_A.toDto())
                    insert(Movie.STUB_B.toDto())
                    insert(Movie.STUB_C.toDto())
                }
                with(db.theaterDao()) {
                    insert(Theater.STUB_A.toDto())
                    insert(Theater.STUB_B.toDto())
                    insert(Theater.STUB_C.toDto())
                }
                with(db.screeningDao()) {
                    insertAll(ScreeningRef.getStubList().map { it.toDto() })
                }
                 */
            }.start()
        }
    }
}
