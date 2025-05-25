package woowacourse.shopping.data.recentlyproducts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recently_products")
data class RecentlyProductsEntity(
    @PrimaryKey @ColumnInfo(name = "product_id") val productId: Long,
    @ColumnInfo(name = "time") val createdAt: Long = System.currentTimeMillis(),
)
