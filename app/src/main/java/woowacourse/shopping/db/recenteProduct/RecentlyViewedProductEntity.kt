package woowacourse.shopping.db.recenteProduct

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recently_viewed_products")
data class RecentlyViewedProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: Int,
    val viewedAt: Long,
)
