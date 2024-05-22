package woowacourse.shopping.presentation.ui.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.base.BaseViewModel
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.base.Event
import woowacourse.shopping.presentation.base.MessageProvider
import woowacourse.shopping.presentation.base.emit
import woowacourse.shopping.presentation.common.ProductCountHandler
import woowacourse.shopping.presentation.ui.productlist.adapter.ProductListPagingSource

class ProductListViewModel(productRepository: ProductRepository) :
    BaseViewModel(),
    ProductListActionHandler,
    ProductCountHandler {
    private val _uiState: MutableLiveData<ProductListUiState> =
        MutableLiveData(ProductListUiState())
    val uiState: LiveData<ProductListUiState> get() = _uiState

    private val _navigateAction: MutableLiveData<Event<ProductListNavigateAction>> =
        MutableLiveData(null)
    val navigateAction: LiveData<Event<ProductListNavigateAction>> get() = _navigateAction

    private val productListPagingSource = ProductListPagingSource(repository = productRepository)

    init {
        loadProductList()
    }

    override fun navigateToProductDetail(productId: Long) {
        _navigateAction.emit(ProductListNavigateAction.NavigateToProductDetail(productId = productId))
    }

    fun navigateToShoppingCart() {
        _navigateAction.emit(ProductListNavigateAction.NavigateToShoppingCart)
    }

    private fun loadProductList() {
        productListPagingSource.load().onSuccess { pagingProduct ->
            _uiState.value?.let { state ->
                val nowPagingProduct =
                    PagingProduct(
                        productList = state.pagingProduct.productList + pagingProduct.productList,
                        last = pagingProduct.last,
                    )
                val cartCount = nowPagingProduct.productList.sumOf { it.quantity }

                _uiState.value =
                    state.copy(
                        pagingProduct = nowPagingProduct,
                        cartCount = state.checkCartCount(cartCount),
                    )
            }
        }.onFailure { e ->
            _uiState.value?.let { state ->
                val newPagingProduct = state.pagingProduct.copy(last = true)
                _uiState.value = state.copy(pagingProduct = newPagingProduct)
            }
            showMessage(MessageProvider.DefaultErrorMessage)
        }
    }

    override fun loadMoreProducts() {
        loadProductList()
    }

    override fun addProductQuantity(
        productId: Long,
        position: Int,
    ) {
        _uiState.value?.let { state ->
            val newProductList =
                state.pagingProduct.productList.map { product ->
                    if (product.id == productId) {
                        product.copy(quantity = product.quantity + 1)
                    } else {
                        product
                    }
                }
            _uiState.value =
                state.copy(
                    pagingProduct = PagingProduct(productList = newProductList),
                    recentlyProductPosition = position,
                    cartCount = state.checkCartCount(1),
                )
        }
    }

    override fun minusProductQuantity(
        productId: Long,
        position: Int,
    ) {
        _uiState.value?.let { state ->
            val newProductList =
                state.pagingProduct.productList.map { product ->
                    if (product.id == productId) {
                        product.copy(quantity = product.quantity - 1)
                    } else {
                        product
                    }
                }
            _uiState.value =
                state.copy(
                    pagingProduct = PagingProduct(productList = newProductList),
                    recentlyProductPosition = position,
                    cartCount = state.checkCartCount(-1),
                )
        }
    }

    companion object {
        fun factory(productRepository: ProductRepository): ViewModelProvider.Factory {
            return BaseViewModelFactory {
                ProductListViewModel(productRepository)
            }
        }
    }
}
