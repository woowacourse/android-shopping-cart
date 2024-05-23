package woowacourse.shopping.ui.products

import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Quantity
import woowacourse.shopping.ui.products.adapter.ProductsViewType

data class ProductUiModel(
    val productId: Long,
    val imageUrl: String,
    val title: String,
    val price: Int,
    val quantity: Quantity,
) : ProductsView {
    override val viewType: ProductsViewType = ProductsViewType.PRODUCTS_UI_MODEL

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
