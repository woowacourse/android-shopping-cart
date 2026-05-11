package woowacourse.shopping.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "products")
data class CatalogEntity(
    @PrimaryKey val productId: UUID,
    val name: String,
    val price: Int,
    val imageUri: String,
)
