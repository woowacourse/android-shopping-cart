package woowacourse.shopping.presentation.ui.shoppingcart

import woowacourse.shopping.domain.model.Product
import java.io.Serializable

data class ShoppingCartUiState(
    val pagingCartProduct: PagingCartProduct = PagingCartProduct(),
    val updatedProducts: UpdatedProducts = UpdatedProducts(),
)

data class PagingCartProduct(
    val products: List<Product> = emptyList(),
    val currentPage: Int = 0,
    val last: Boolean = true,
)

data class UpdatedProducts(private val products: MutableMap<Long, Product> = mutableMapOf()) :
    Serializable {
    fun addProduct(other: Product) {
        products[other.id] = other
    }

    fun getProduct(productId: Long): Product? {
        return products[productId]
    }
}
