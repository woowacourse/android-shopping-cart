package woowacourse.shopping.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.ShoppingApp
import woowacourse.shopping.domain.model.CatalogProducts
import woowacourse.shopping.domain.model.CatalogProducts.Companion.EMPTY_CATALOG_PRODUCTS
import woowacourse.shopping.domain.model.HistoryProduct
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.HistoryRepository
import woowacourse.shopping.domain.repository.ProductRepository

class ProductsViewModel(
    private val productsRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val historyRepository: HistoryRepository,
) : ViewModel() {
    private val _catalogProducts: MutableLiveData<CatalogProducts> = MutableLiveData(EMPTY_CATALOG_PRODUCTS)
    val catalogProducts: LiveData<CatalogProducts> get() = _catalogProducts

    private val _historyProducts: MutableLiveData<List<HistoryProduct>> = MutableLiveData(emptyList())
    val historyProducts: LiveData<List<HistoryProduct>> get() = _historyProducts

    private val maxProductId: Int
        get() =
            catalogProducts.value
                ?.products
                ?.lastOrNull()
                ?.product
                ?.id ?: 0

    fun loadProducts(count: Int = SHOWN_PRODUCTS_COUNT) {
        productsRepository.fetchProducts(lastId = maxProductId, count = count) { newProducts ->
            _catalogProducts.postValue(
                CatalogProducts(
                    products = (catalogProducts.value?.products ?: emptyList()) + newProducts.products,
                    hasMore = newProducts.hasMore,
                ),
            )
        }
    }

    fun loadHistoryProducts() {
        historyRepository.fetchAllSearchHistory { historyProducts ->
            _historyProducts.postValue(historyProducts)
        }
    }

    fun increaseCartProduct(id: Int) {
        cartRepository.increaseProductQuantity(id) { newQuantity ->
            _catalogProducts.postValue(
                CatalogProducts(
                    products = updateCartProductQuantity(id, newQuantity),
                    hasMore = catalogProducts.value?.hasMore ?: false,
                ),
            )
        }
    }

    private fun updateCartProductQuantity(
        id: Int,
        newQuantity: Int,
    ) = catalogProducts.value?.products?.map { product ->
        if (product.product.id == id) {
            product.copy(quantity = newQuantity)
        } else {
            product
        }
    } ?: emptyList()

    fun decreaseCartProduct(id: Int) {
        cartRepository.decreaseProductQuantity(id) { newQuantity ->
            _catalogProducts.postValue(
                CatalogProducts(
                    products = updateCartProductQuantity(id, newQuantity),
                    hasMore = catalogProducts.value?.hasMore ?: false,
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
                    val application = checkNotNull(extras[APPLICATION_KEY]) as ShoppingApp

                    return ProductsViewModel(
                        application.productRepository,
                        application.cartRepository,
                        application.historyRepository,
                    ) as T
                }
            }
    }
}
