package woowacourse.shopping.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.ProvidedTypeConverter
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import woowacourse.shopping.data.db.cart.CartDao
import woowacourse.shopping.data.db.cart.CartEntity
import woowacourse.shopping.data.db.recent.RecentProductDao
import woowacourse.shopping.data.db.recent.RecentProductEntity
import woowacourse.shopping.domain.Product
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Database(
    entities = [RecentProductEntity::class, CartEntity::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(value = [LocalDateTimeTypeConverter::class, ProductTypeConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun recentProductDao(): RecentProductDao

    abstract fun cartDao(): CartDao

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

class ProductTypeConverter {
    @TypeConverter
    fun fromProductToJson(value: Product): String {
        val gson = Gson()
        val type = object : TypeToken<Product>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun fromJsonToProduct(value: String): Product {
        val gson = Gson()
        val type = object : TypeToken<Product>() {}.type
        return gson.fromJson(value, type)
    }
}
