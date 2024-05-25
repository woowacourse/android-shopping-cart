package woowacourse.shopping.ui.products.adapter.type

import woowacourse.shopping.data.product.entity.Product
import woowacourse.shopping.model.Quantity

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
