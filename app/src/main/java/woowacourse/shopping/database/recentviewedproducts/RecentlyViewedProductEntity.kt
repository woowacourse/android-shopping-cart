package woowacourse.shopping.database.recentviewedproducts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "recently_viewed_products")
data class RecentlyViewedProductEntity(
    @PrimaryKey
    val id: Long,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "price")
    val price: Int,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    @ColumnInfo(name = "viewed_time")
    val viewedTime: LocalDateTime,
)
