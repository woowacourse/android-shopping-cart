package woowacourse.shopping.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    val name: String,
    val imgUrl: String,
    val price: Long,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
