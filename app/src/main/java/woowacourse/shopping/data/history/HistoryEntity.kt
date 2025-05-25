package woowacourse.shopping.data.history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo("createdAt") val createdAt: Long = System.currentTimeMillis(),
)
