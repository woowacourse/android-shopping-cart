package woowacourse.shopping.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val imageUrl: String,
    val price: Int,
)
