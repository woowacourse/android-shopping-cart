package woowacourse.shopping.productDetail

import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.Product
import woowacourse.shopping.repository.ShoppingCartItemRepository
import woowacourse.shopping.repository.ShoppingProductsRepository

class ProductDetailViewModel(
    productId: Int,
    shoppingProductsRepository: ShoppingProductsRepository,
    private val shoppingCartItemRepository: ShoppingCartItemRepository,
) : ViewModel() {
    val product: Product = shoppingProductsRepository.findById(productId)

    fun addProductToCart() {
        shoppingCartItemRepository.addCartItem(product)
    }
}
