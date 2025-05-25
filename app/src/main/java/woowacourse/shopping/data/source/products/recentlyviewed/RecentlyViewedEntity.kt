package woowacourse.shopping.data.source.products.recentlyviewed

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recently_viewed")
class RecentlyViewedEntity(
    @PrimaryKey val productId: Long,
    @ColumnInfo(name = "viewed_at") val viewedAt: Long = System.currentTimeMillis(),
)
