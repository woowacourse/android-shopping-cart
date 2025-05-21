package woowacourse.shopping.data.products

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_product")
data class RecentProduct(
    @PrimaryKey val id: Int,
)
