package woowacourse.shopping.data.db.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["productId"], unique = true)])
data class ProductBrowsingHistoryEntity(
    @PrimaryKey(autoGenerate = true) val historyId: Int = 0,
    @Embedded
    val product: ProductEntity,
    val timestamp: Long,
)
