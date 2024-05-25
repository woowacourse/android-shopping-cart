package woowacourse.shopping.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_cart_item")
data class ShoppingCartItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @Embedded("product_")
    val product: ProductEntity,
    @ColumnInfo("totalQuantity")
    val totalQuantity: Int,
) {
    companion object {
        val STUB_LIST = makeSTUBS()
        val STUB = STUB_LIST.first()

        private fun makeSTUBS(): List<ShoppingCartItemEntity> =
            ProductEntity.STUB_LIST.subList(0, 7).mapIndexed { index, productEntity ->
                ShoppingCartItemEntity(product = productEntity, totalQuantity = 1)
            }
    }
}
