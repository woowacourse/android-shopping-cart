package woowacourse.shopping.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.LocalDateTime

@Entity(tableName = "recentProducts")
data class RecentProductEntity(
    @PrimaryKey val productId: Long,
    @ColumnInfo(name = "createdTime") val createdTime: LocalDateTime,
) : Serializable
