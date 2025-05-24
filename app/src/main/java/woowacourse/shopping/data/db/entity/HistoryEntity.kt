package woowacourse.shopping.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val productId: Long,
    val createdAt: Long = System.currentTimeMillis(),
)
