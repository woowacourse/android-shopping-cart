package woowacourse.shopping.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "order")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val key: Long = 0,
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "quantity") val quantity: Int,
) : Serializable
