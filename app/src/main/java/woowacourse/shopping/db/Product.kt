package woowacourse.shopping.db

import androidx.room.PrimaryKey

data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val imgURL: String,
    val name: String,
    val price: Int
)
