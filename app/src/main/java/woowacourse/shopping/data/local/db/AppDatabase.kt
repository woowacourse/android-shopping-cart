package woowacourse.shopping.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import woowacourse.shopping.data.local.dao.CartProductDao
import woowacourse.shopping.data.local.dao.RecentProductDao
import woowacourse.shopping.data.local.entity.CartEntity
import woowacourse.shopping.data.local.entity.CartProductEntity
import woowacourse.shopping.data.local.entity.ProductEntity
import woowacourse.shopping.data.local.entity.RecentEntity
import woowacourse.shopping.data.local.entity.RecentProductEntity
import java.lang.IllegalStateException

@Database(
    entities = [CartEntity::class, CartProductEntity::class, ProductEntity::class, RecentProductEntity::class, RecentEntity::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartProductDao(): CartProductDao

    abstract fun recentProductDao(): RecentProductDao

    companion object {
        @Volatile
        private var _instance: AppDatabase? = null
        val instance: AppDatabase get() = _instance ?: throw IllegalStateException("DB is not initialized")

        fun init(context: Context): AppDatabase {
            return _instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "db")
                    .fallbackToDestructiveMigration()
                    .addCallback(
                        object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)

                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('계란 30구', " +
                                        "'https://cdn.aflnews.co.kr/news/photo/202002/166816_32395_3813.jpg', '10000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('신나는 떡볶이', " +
                                        "'https://mediahub.seoul.go.kr/wp-content/uploads" +
                                        "/2020/10/d13ea4a756099add8375e6c795b827ab.jpg', '14000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('쌀국수', " +
                                        "'https://www.info-toyama.com/storage/special_features/462/responsive_images" +
                                        "/iBOCJNctT7wbmzQi3sOvJNko5ewfzBcbm1lrzGuP__1652_1239.jpeg', '15000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('비빔밥', " +
                                        "'https://m.segye.com/content/image/2021/01/07/20210107516500.jpg', '9900')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('짜장면', " +
                                        "'https://s3.ap-northeast-2.amazonaws.com/img.kormedi.com/news/article/" +
                                        "__icsFiles/artimage/2015/05/23/c_km601/432212_540.jpg', '6000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('삼겹살', " +
                                        "'https://cdn.mindgil.com/news/photo/202004/69068_2873_1455.jpg', '12000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('된장찌개', " +
                                        "'https://kccuk.org.uk/media/images/Food_1.width-840.jpg', '12000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('참치', " +
                                        "'https://migrationology.com/wp-content/uploads/2015/10/tuna-belly-izakaya-osaka.jpg', '22000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('가라아게', " +
                                        "'https://c.files.bbci.co.uk/6DDA/production/" +
                                        "_128122182_e2c664e8-7e01-4e27-a1a4-7a6b4b99258d.jpg', '15500')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('계란 30구', " +
                                        "'https://cdn.aflnews.co.kr/news/photo/202002/166816_32395_3813.jpg', '10000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('신나는 떡볶이', " +
                                        "'https://mediahub.seoul.go.kr/wp-content/uploads/" +
                                        "2020/10/d13ea4a756099add8375e6c795b827ab.jpg', '14000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('쌀국수', " +
                                        "'https://www.info-toyama.com/storage/special_features/462/" +
                                        "responsive_images/iBOCJNctT7wbmzQi3sOvJNko5ewfzBcbm1lrzGuP__1652_1239.jpeg', '15000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('비빔밥', " +
                                        "'https://m.segye.com/content/image/2021/01/07/20210107516500.jpg', '9900')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('짜장면', " +
                                        "'https://s3.ap-northeast-2.amazonaws.com/img.kormedi.com/news/article/" +
                                        "__icsFiles/artimage/2015/05/23/c_km601/432212_540.jpg', '6000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('삼겹살', " +
                                        "'https://cdn.mindgil.com/news/photo/202004/69068_2873_1455.jpg', '12000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('된장찌개', " +
                                        "'https://kccuk.org.uk/media/images/Food_1.width-840.jpg', '12000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('참치', " +
                                        "'https://migrationology.com/wp-content/uploads/2015/10/tuna-belly-izakaya-osaka.jpg', '22000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('가라아게', " +
                                        "'https://c.files.bbci.co.uk/6DDA/production/" +
                                        "_128122182_e2c664e8-7e01-4e27-a1a4-7a6b4b99258d.jpg', '15500')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('계란 30구', " +
                                        "'https://cdn.aflnews.co.kr/news/photo/202002/166816_32395_3813.jpg', '10000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('신나는 떡볶이', " +
                                        "'https://mediahub.seoul.go.kr/wp-content/uploads/2020/" +
                                        "10/d13ea4a756099add8375e6c795b827ab.jpg', '14000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('쌀국수', " +
                                        "'https://www.info-toyama.com/storage/special_features/462/" +
                                        "responsive_images/iBOCJNctT7wbmzQi3sOvJNko5ewfzBcbm1lrzGuP__1652_1239.jpeg', '15000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('비빔밥', " +
                                        "'https://m.segye.com/content/image/2021/01/07/20210107516500.jpg', '9900')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('짜장면', " +
                                        "'https://s3.ap-northeast-2.amazonaws.com/img.kormedi.com/news/article/" +
                                        "__icsFiles/artimage/2015/05/23/c_km601/432212_540.jpg', '6000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('삼겹살', " +
                                        "'https://cdn.mindgil.com/news/photo/202004/69068_2873_1455.jpg', '12000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('된장찌개', " +
                                        "'https://kccuk.org.uk/media/images/Food_1.width-840.jpg', '12000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('참치', " +
                                        "'https://migrationology.com/wp-content/uploads/2015/10/tuna-belly-izakaya-osaka.jpg', '22000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('가라아게', " +
                                        "'https://c.files.bbci.co.uk/6DDA/production/_" +
                                        "128122182_e2c664e8-7e01-4e27-a1a4-7a6b4b99258d.jpg', '15500')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('계란 30구', " +
                                        "'https://cdn.aflnews.co.kr/news/photo/202002/166816_32395_3813.jpg', '10000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('신나는 떡볶이', " +
                                        "'https://mediahub.seoul.go.kr/wp-content/uploads/2020/10" +
                                        "/d13ea4a756099add8375e6c795b827ab.jpg', '14000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('쌀국수', " +
                                        "'https://www.info-toyama.com/storage/special_features/462/" +
                                        "responsive_images/iBOCJNctT7wbmzQi3sOvJNko5ewfzBcbm1lrzGuP__1652_1239.jpeg', '15000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('비빔밥', " +
                                        "'https://m.segye.com/content/image/2021/01/07/20210107516500.jpg', '9900')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('짜장면', " +
                                        "'https://s3.ap-northeast-2.amazonaws.com/img.kormedi.com/news/article/" +
                                        "__icsFiles/artimage/2015/05/23/c_km601/432212_540.jpg', '6000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('삼겹살', " +
                                        "'https://cdn.mindgil.com/news/photo/202004/69068_2873_1455.jpg', '12000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('된장찌개', " +
                                        "'https://kccuk.org.uk/media/images/Food_1.width-840.jpg', '12000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('참치', " +
                                        "'https://migrationology.com/wp-content/uploads/2015/10/tuna-belly-izakaya-osaka.jpg', '22000')",
                                )
                                db.execSQL(
                                    "INSERT INTO ProductEntity (name, imgUrl, price) values ('가라아게', " +
                                        "'https://c.files.bbci.co.uk/6DDA/production/_128122182" +
                                        "_e2c664e8-7e01-4e27-a1a4-7a6b4b99258d.jpg', '15500')",
                                )
                            }
                        },
                    )
                    .build()
                    .also { _instance = it }
            }
        }
    }
}
