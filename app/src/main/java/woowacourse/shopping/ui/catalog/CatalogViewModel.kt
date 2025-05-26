package woowacourse.shopping.ui.catalog

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
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.usecase.DecreaseCartProductQuantityUseCase
import woowacourse.shopping.domain.usecase.GetSearchHistoryUseCase
import woowacourse.shopping.domain.usecase.IncreaseCartProductQuantityUseCase

class CatalogViewModel(
    private val productRepository: ProductRepository,
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val increaseCartProductQuantityUseCase: IncreaseCartProductQuantityUseCase,
    private val decreaseCartProductQuantityUseCase: DecreaseCartProductQuantityUseCase,
) : ViewModel() {
    private val _catalogProducts: MutableLiveData<CatalogProducts> = MutableLiveData(EMPTY_CATALOG_PRODUCTS)
    val catalogProducts: LiveData<CatalogProducts> get() = _catalogProducts

    private val _historyProducts: MutableLiveData<List<HistoryProduct>> = MutableLiveData(emptyList())
    val historyProducts: LiveData<List<HistoryProduct>> get() = _historyProducts

    private val lastProductId: Int
        get() =
            catalogProducts.value
                ?.products
                ?.lastOrNull()
                ?.product
                ?.id ?: 0

    fun loadCartProducts(count: Int = SHOWN_PRODUCTS_COUNT) {
        productRepository.fetchProducts(lastId = lastProductId, count = count) { newProducts ->
            _catalogProducts.postValue(catalogProducts.value?.plus(newProducts))
        }
    }

    fun loadHistoryProducts() {
        getSearchHistoryUseCase { historyProducts ->
            _historyProducts.postValue(historyProducts)
        }
    }

    fun increaseCartProduct(id: Int) {
        increaseCartProductQuantityUseCase(id) { newQuantity ->
            _catalogProducts.postValue(catalogProducts.value?.updateCatalogProductQuantity(id, newQuantity))
        }
    }

    fun decreaseCartProduct(id: Int) {
        decreaseCartProductQuantityUseCase(id) { newQuantity ->
            _catalogProducts.postValue(catalogProducts.value?.updateCatalogProductQuantity(id, newQuantity))
        }
    }

    fun loadCartProduct(id: Int) {
        productRepository.fetchProduct(id) { cartProduct ->
            _catalogProducts.postValue(catalogProducts.value?.updateCatalogProduct(cartProduct ?: return@fetchProduct))
        }
    }

    fun loadCartProducts(ids: List<Int>) {
        productRepository.fetchProducts(ids) { cartProducts ->
            _catalogProducts.postValue(catalogProducts.value?.updateCatalogProducts(cartProducts))
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

                    return CatalogViewModel(
                        productRepository = application.productRepository,
                        getSearchHistoryUseCase = application.getSearchHistoryUseCase,
                        increaseCartProductQuantityUseCase = application.increaseCartProductQuantityUseCase,
                        decreaseCartProductQuantityUseCase = application.decreaseCartProductQuantityUseCase,
                    ) as T
                }
            }
    }
}
