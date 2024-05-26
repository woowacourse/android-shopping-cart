package woowacourse.shopping.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [InquiryHistoryEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class InquiryHistoryDatabase : RoomDatabase() {
    abstract fun recentViewedProductDao(): InquiryHistoryDao

    companion object {
        fun initialize(context: Context) =
            Room.databaseBuilder(
                context,
                InquiryHistoryDatabase::class.java,
                "inquiry_history",
            ).build()
    }
}
