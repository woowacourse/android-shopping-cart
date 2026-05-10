package woowacourse.shopping.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "viewed_products")
data class ViewedProductEntity(
    @PrimaryKey
    @ColumnInfo("product_id")
    val productId: String,
    @ColumnInfo("viewed_at")
    val viewedAt: Long,
)
