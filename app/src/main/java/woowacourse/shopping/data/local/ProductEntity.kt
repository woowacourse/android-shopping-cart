package woowacourse.shopping.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class ProductEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo("image_url") val imageUrl: String,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("price") val price: Int,
)
