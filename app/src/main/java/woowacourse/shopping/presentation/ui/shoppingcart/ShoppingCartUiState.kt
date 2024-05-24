package woowacourse.shopping.presentation.ui.shoppingcart

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.shopping.domain.model.Product

data class ShoppingCartUiState(
    val pagingCartProduct: PagingCartProduct = PagingCartProduct(),
    val updatedProducts: UpdatedProducts = UpdatedProducts(),
)

data class PagingCartProduct(
    val products: List<Product> = emptyList(),
    val currentPage: Int = 0,
    val last: Boolean = true,
)

@Parcelize
data class UpdatedProducts(private val products: MutableMap<Long, Product> = mutableMapOf()) :
    Parcelable {
    fun addProduct(other: Product) {
        products[other.id] = other
    }

    fun getProduct(productId: Long): Product? {
        return products[productId]
    }
}
