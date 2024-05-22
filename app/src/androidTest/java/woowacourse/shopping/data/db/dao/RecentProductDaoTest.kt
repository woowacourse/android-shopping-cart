package woowacourse.shopping.data.db.dao

import androidx.room.Room
import org.junit.jupiter.api.BeforeEach
import woowacourse.shopping.data.db.ShoppingDatabase
import woowacourse.shopping.util.testApplicationContext

//     @Query("SELECT * FROM RecentProduct ORDER BY createTime DESC LIMIT :size")
//    fun loadProducts(size: Int): List<RecentProductEntity>
//
//    @Insert
//    fun saveProduct(product: RecentProductEntity)
class RecentProductDaoTest {
    lateinit var recentProductDao: RecentProductDao

    @BeforeEach
    fun setUp() {
        val db = Room.inMemoryDatabaseBuilder(
            testApplicationContext,
            ShoppingDatabase::class.java
        ).build()
        recentProductDao = db.recentProductDao()
    }
}