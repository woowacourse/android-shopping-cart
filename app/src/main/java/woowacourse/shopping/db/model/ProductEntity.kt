package woowacourse.shopping.db.model

import androidx.room.PrimaryKey

data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val imgUrl: String,
    val name: String,
    val price: Int,
)
