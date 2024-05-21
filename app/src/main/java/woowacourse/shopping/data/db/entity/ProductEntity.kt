package woowacourse.shopping.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val price: Int,
    val imageUrl: String,
)
