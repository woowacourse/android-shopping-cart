package woowacourse.shopping.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.shopping.data.db.entity.RecentProductEntity

@Dao
interface RecentProductDao {

    @Query("SELECT * FROM RecentProduct ORDER BY createdTime DESC LIMIT :size")
    fun loadProducts(size: Int): List<RecentProductEntity>

    @Insert
    fun saveProduct(product: RecentProductEntity)
}

