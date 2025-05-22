package woowacourse.shopping.view.product.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.cart.CartProductRepository
import woowacourse.shopping.data.product.ProductRepository

class ProductCatalogViewModelFactory(
    private val productRepository: ProductRepository,
    private val cartProductRepository: CartProductRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductCatalogViewModel::class.java)) {
            return ProductCatalogViewModel(productRepository, cartProductRepository) as T
        }
        throw IllegalArgumentException()
    }
}
