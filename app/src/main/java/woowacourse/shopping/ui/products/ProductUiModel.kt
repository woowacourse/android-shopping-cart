package woowacourse.shopping.ui.products

import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Quantity

data class ProductUiModel(
    val productId: Long,
    val imageUrl: String,
    val title: String,
    val price: Int,
    val quantity: Quantity,
) {
    fun totalPrice() = price * quantity.count

    companion object {
        fun from(
            product: Product,
            quantity: Quantity = Quantity(),
        ): ProductUiModel {
            return ProductUiModel(product.id, product.imageUrl, product.title, product.price, quantity)
        }
    }
}
