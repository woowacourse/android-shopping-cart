package woowacourse.shopping.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.ShoppingApp
import woowacourse.shopping.domain.model.Products
import woowacourse.shopping.domain.model.Products.Companion.EMPTY_PRODUCTS
import woowacourse.shopping.domain.repository.ProductRepository

class ProductsViewModel(
    private val productsDummyRepository: ProductRepository,
) : ViewModel() {
    private val _products: MutableLiveData<Products> = MutableLiveData(EMPTY_PRODUCTS)
    val products: LiveData<Products> get() = _products

    private val maxProductId: Int get() =
        products.value
            ?.products
            ?.lastOrNull()
            ?.id ?: 0

    fun loadProducts(count: Int = SHOWN_PRODUCTS_COUNT) {
        productsDummyRepository.fetchProducts(lastId = maxProductId, count = count) { newProducts ->
            _products.postValue(
                Products(
                    products = (products.value?.products ?: emptyList()) + newProducts.products,
                    hasMore = newProducts.hasMore,
                ),
            )
        }
    }

    companion object {
        private const val SHOWN_PRODUCTS_COUNT: Int = 20

        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    val application = checkNotNull(extras[APPLICATION_KEY])

                    return ProductsViewModel(
                        (application as ShoppingApp).productRepository,
                    ) as T
                }
            }
    }
}
