package woowacourse.shopping.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.ProvidedTypeConverter
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Database(
    entities = [RecentProductEntity::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(value = [LocalDateTimeTypeConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun recentProductDao(): RecentProductDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        val instanceOrNull get() = instance ?: throw IllegalArgumentException()

        fun init(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance =
                        Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "app_database",
                        ).addTypeConverter(LocalDateTimeTypeConverter(Gson()))
                            .build()
                }
            }
            return instance
        }
    }
}

@ProvidedTypeConverter
class LocalDateTimeTypeConverter(private val gson: Gson) {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun localDateTimeToString(localDateTime: LocalDateTime): String = formatter.format(localDateTime)

    @TypeConverter
    fun stringToLocalDateTime(string: String): LocalDateTime = LocalDateTime.parse(string, formatter)
}
