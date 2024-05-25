package woowacourse.shopping.data.db.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["historyId", "productId", "timestamp"], unique = true)])
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val historyId: Int = 0,
    @Embedded
    val product: ProductEntity,
    val timestamp: Long,
) {
}
