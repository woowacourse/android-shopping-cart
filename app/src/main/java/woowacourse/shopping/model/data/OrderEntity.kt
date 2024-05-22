package woowacourse.shopping.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "order")
data class OrderEntity(
    @PrimaryKey val productId: Long,
    @ColumnInfo(name = "quantity") val quantity: Int,
) : Serializable
