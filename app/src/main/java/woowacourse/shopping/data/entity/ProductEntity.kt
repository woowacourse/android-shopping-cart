package woowacourse.shopping.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val imageUrl: String,
    val price: Int,
)
