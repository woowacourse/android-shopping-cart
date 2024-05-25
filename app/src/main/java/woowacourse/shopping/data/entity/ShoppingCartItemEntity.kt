package woowacourse.shopping.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_cart_item")
data class ShoppingCartItemEntity(
    @PrimaryKey
    @ColumnInfo("product_id") val productId: Long,
    @ColumnInfo("totalQuantity")
    val totalQuantity: Int,
) {
    companion object {
        val STUB_LIST = makeSTUBS()
        val STUB = STUB_LIST.first()

        private fun makeSTUBS(): List<ShoppingCartItemEntity> =
            (0..6).map {
                ShoppingCartItemEntity(productId = it.toLong(), totalQuantity = 1)
            }
    }
}
