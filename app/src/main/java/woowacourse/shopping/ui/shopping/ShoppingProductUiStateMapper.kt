package woowacourse.shopping.ui.shopping

import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductId

object ShoppingProductUiStateMapper {
    fun toUiStates(
        products: List<Product>,
        quantityByProductId: Map<ProductId, Int>,
    ): List<ShoppingProductUiState> =
        products.map { product ->
            ShoppingProductUiState(
                product = product,
                quantity = quantityByProductId[product.id] ?: 0,
            )
        }
}
