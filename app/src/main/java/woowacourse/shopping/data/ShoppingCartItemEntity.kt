package woowacourse.shopping.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.model.products.Product
import woowacourse.shopping.model.products.ShoppingCartItem

@Entity(tableName = "shopping_cart_items")
data class ShoppingCartItemEntity(
    @PrimaryKey(autoGenerate = true) val productId: Int = 0,
    @ColumnInfo(name = "product_name") val productName: String,
    @ColumnInfo(name = "product_image_url") val productImageUrl: String,
    @ColumnInfo(name = "product_price") val productPrice: Int,
    @ColumnInfo(name = "product_quantity") val productQuantity: Int,
) {
    fun toDomainModel(): ShoppingCartItem {
        val product =
            Product(
                id = productId,
                title = productName,
                imageUrl = productImageUrl,
                price = productPrice,
            )
        return ShoppingCartItem(
            product = product,
            quantity = productQuantity,
        )
    }

    companion object {
        fun fromDomainModel(shoppingCartItem: ShoppingCartItem): ShoppingCartItemEntity =
            ShoppingCartItemEntity(
                productId = shoppingCartItem.product.id,
                productName = shoppingCartItem.product.title,
                productImageUrl = shoppingCartItem.product.imageUrl,
                productPrice = shoppingCartItem.product.price,
                productQuantity = shoppingCartItem.quantity,
            )
    }
}
