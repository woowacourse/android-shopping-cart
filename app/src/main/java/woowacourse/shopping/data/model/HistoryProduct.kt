package woowacourse.shopping.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_products")
data class HistoryProduct(
    @PrimaryKey(autoGenerate = false)
    val id: Long = -1,
    val timestamp: Long = System.currentTimeMillis(),
)
