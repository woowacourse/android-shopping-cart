package woowacourse.shopping.presentation.shopping.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.entity.CartProduct
import woowacourse.shopping.domain.entity.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.shopping.detail.ProductUi
import woowacourse.shopping.presentation.shopping.toShoppingUiModel
import woowacourse.shopping.presentation.shopping.toUiModel
import woowacourse.shopping.presentation.util.MutableSingleLiveData
import woowacourse.shopping.presentation.util.SingleLiveData

class ProductListViewModel(
    private val shoppingRepository: ShoppingRepository,
    private val cartRepository: CartRepository,
) : ViewModel(), ProductItemListener {
    private val _uiState = MutableLiveData(ProductListUiState(START_PAGE))
    val uiState: LiveData<ProductListUiState> get() = _uiState

    private val _navigateToDetailEvent = MutableSingleLiveData<Long>()
    val navigateToDetailEvent: SingleLiveData<Long> = _navigateToDetailEvent
    private val _errorEvent = MutableSingleLiveData<ProductListErrorEvent>()
    val errorEvent: SingleLiveData<ProductListErrorEvent> = _errorEvent

    init {
        loadProducts()
        loadCartProducts()
        loadRecentProducts()
    }

    override fun loadProducts() {
        val uiState = _uiState.value ?: return
        val currentPage = uiState.currentPage
        shoppingRepository.products(currentPage, PAGE_SIZE)
            .onSuccess {
                val newProducts = it.map(Product::toShoppingUiModel)
                _uiState.value = uiState.addProducts(newProducts, getLoadMore(currentPage + 1))
            }.onFailure {
                _errorEvent.setValue(ProductListErrorEvent.LoadProducts)
            }
    }

    fun loadCartProducts() {
        val uiState = uiState.value ?: return
        val ids = uiState.products.map { it.id }
        cartRepository.filterCartProducts(ids)
            .onSuccess { newCartProducts ->
                val newProducts = newCartProducts.map(CartProduct::toShoppingUiModel)
                _uiState.value = uiState.updateCartProducts(newProducts)
            }.onFailure {
                _errorEvent.setValue(ProductListErrorEvent.LoadCartProducts)
            }
    }

    override fun increaseProductCount(id: Long) {
        val uiState = _uiState.value ?: return
        val product = uiState.findProduct(id) ?: return
        cartRepository.updateCartProduct(id, product.count + INCREMENT_AMOUNT).onSuccess {
            _uiState.value = uiState.increaseProductCount(id, INCREMENT_AMOUNT)
        }.onFailure {
            _errorEvent.setValue(ProductListErrorEvent.DecreaseCartCount)
        }
    }

    override fun decreaseProductCount(id: Long) {
        val uiState = _uiState.value ?: return
        if (uiState.shouldDeleteFromCart(id)) {
            cartRepository.deleteCartProduct(id).onSuccess {
                _uiState.value = uiState.decreaseProductCount(id, INCREMENT_AMOUNT)
            }
            return
        }
        val product = uiState.findProduct(id) ?: return
        cartRepository.updateCartProduct(id, product.count - INCREMENT_AMOUNT).onSuccess {
            _uiState.value = uiState.decreaseProductCount(id, INCREMENT_AMOUNT)
        }.onFailure {
            _errorEvent.setValue(ProductListErrorEvent.DecreaseCartCount)
        }
    }

    override fun navigateToDetail(id: Long) {
        _navigateToDetailEvent.setValue(id)
    }

    fun loadRecentProducts() {
        val uiState = _uiState.value ?: return
        shoppingRepository.recentProducts(RECENT_PRODUCT_COUNT).onSuccess {
            _uiState.value = uiState.copy(recentProducts = it.map(Product::toUiModel))
        }.onFailure {
            _errorEvent.setValue(ProductListErrorEvent.LoadRecentProducts)
        }
    }

    private fun getLoadMore(page: Int): List<ShoppingUiModel.LoadMore> {
        return if (shoppingRepository.canLoadMore(page, PAGE_SIZE).getOrNull() == true) {
            listOf(ShoppingUiModel.LoadMore)
        } else {
            emptyList()
        }
    }

    companion object {
        private const val RECENT_PRODUCT_COUNT = 10
        private const val PAGE_SIZE = 20
        private const val INCREMENT_AMOUNT = 1
        private const val START_PAGE = 1

        fun factory(
            shoppingRepository: ShoppingRepository,
            cartRepository: CartRepository,
        ): ViewModelProvider.Factory {
            return BaseViewModelFactory { ProductListViewModel(shoppingRepository, cartRepository) }
        }
    }
}

data class ProductListUiState(
    val currentPage: Int = 1,
    val totalProducts: List<ShoppingUiModel> = emptyList(),
    val recentProducts: List<ProductUi> = emptyList(),
) {
    val products: List<ShoppingUiModel.Product>
        get() = totalProducts.filterIsInstance<ShoppingUiModel.Product>()

    private val loadMoreModels: List<ShoppingUiModel.LoadMore>
        get() = totalProducts.filterIsInstance<ShoppingUiModel.LoadMore>()

    fun addProducts(
        newProducts: List<ShoppingUiModel.Product>,
        loadMore: List<ShoppingUiModel.LoadMore>,
    ): ProductListUiState {
        return copy(
            currentPage = currentPage + 1,
            totalProducts = products + newProducts + loadMore,
        )
    }

    fun updateCartProducts(newCartProducts: List<ShoppingUiModel.Product>): ProductListUiState {
        return copy(
            totalProducts =
                products.map { originalProduct ->
                    val newProduct =
                        newCartProducts.find { newProduct -> newProduct.id == originalProduct.id }
                            ?: return@map originalProduct.copy(count = 0)
                    originalProduct.copy(count = newProduct.count)
                } + loadMoreModels,
        )
    }

    fun increaseProductCount(
        productId: Long,
        amount: Int,
    ): ProductListUiState =
        copy(
            totalProducts =
                totalProducts.map {
                    if (it is ShoppingUiModel.Product && it.id == productId) {
                        it.copy(count = it.count + amount)
                    } else {
                        it
                    }
                },
        )

    fun decreaseProductCount(
        productId: Long,
        amount: Int,
    ): ProductListUiState {
        val newProducts =
            totalProducts.map {
                if (it is ShoppingUiModel.Product && it.id == productId) {
                    it.copy(count = it.count - amount)
                } else {
                    it
                }
            }
        return copy(totalProducts = newProducts)
    }

    fun shouldDeleteFromCart(productId: Long): Boolean {
        val product = findProduct(productId) ?: return false
        return product.count <= 1
    }

    fun findProduct(productId: Long): ShoppingUiModel.Product? =
        totalProducts.filterIsInstance<ShoppingUiModel.Product>().find { it.id == productId }
}
