package woowacourse.shopping.view.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository

class MainViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _uiState = MutableLiveData(ProductUiState())
    val uiState: LiveData<ProductUiState> = _uiState

    init {
        loadProducts()
    }

    fun loadProducts() {
        val newPage = _uiState.value?.itemCount()?.div(PAGE_SIZE) ?: 0

        val productsResult = productRepository.loadSinglePage(newPage, PAGE_SIZE)

        val result =
            productsResult
                .products
                .map { product ->
                    val cart = cartRepository[product.id]
                    val quantity = cart?.quantity ?: Quantity(0)

                    ProductState(product, quantity)
                }

        _uiState.value = _uiState.value?.addItems(result, productsResult.hasNextPage)
    }

    fun increaseQuantity(productId: Long) {
        _uiState.value = _uiState.value?.increaseQuantity(productId)
    }

    fun decreaseQuantity(productId: Long) {
        _uiState.value = _uiState.value?.decreaseQuantity(productId)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
