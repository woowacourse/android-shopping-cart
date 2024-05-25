package woowacourse.shopping.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val productId: Int,
    val name: String,
    val price: Int,
    val imageUrl: String,
)
