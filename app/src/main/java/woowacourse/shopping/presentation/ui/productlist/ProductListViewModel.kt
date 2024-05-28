package woowacourse.shopping.presentation.ui.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductHistoryRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.base.BaseViewModel
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.base.Event
import woowacourse.shopping.presentation.base.MessageProvider
import woowacourse.shopping.presentation.base.emit
import woowacourse.shopping.presentation.common.ProductCountHandler
import woowacourse.shopping.presentation.ui.productlist.adapter.ProductListPagingSource
import kotlin.concurrent.thread

class ProductListViewModel(
    productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
    private val productHistoryRepository: ProductHistoryRepository,
) : BaseViewModel(), ProductListActionHandler, ProductCountHandler {
    private val _uiState: MutableLiveData<ProductListUiState> =
        MutableLiveData(ProductListUiState())
    val uiState: LiveData<ProductListUiState> get() = _uiState

    private val _navigateAction: MutableLiveData<Event<ProductListNavigateAction>> =
        MutableLiveData(null)
    val navigateAction: LiveData<Event<ProductListNavigateAction>> get() = _navigateAction

    private val productListPagingSource =
        ProductListPagingSource(
            productRepository = productRepository,
            shoppingCartRepository = shoppingCartRepository,
        )

    init {
        initLoad()
    }

    private fun initLoad() {
        thread {
            productListPagingSource.load().mapCatching { pagingProduct ->
                val productHistorys =
                    productHistoryRepository.getProductHistory(10).getOrDefault(emptyList())
                val cartCount = pagingProduct.productList.sumOf { it.quantity }

                ProductListUiState(
                    pagingProduct = pagingProduct,
                    productHistorys = productHistorys,
                    cartCount = cartCount,
                )
            }.onSuccess { productListUiState ->
                hideError()
                _uiState.postValue(productListUiState)
            }.onFailure { e ->
                showError(e)
                showMessage(MessageProvider.DefaultErrorMessage)
            }
        }
    }

    override fun retry() {
        loadMoreProducts()
    }

    override fun navigateToProductDetail(productId: Long) {
        _navigateAction.emit(ProductListNavigateAction.NavigateToProductDetail(productId = productId))
    }

    fun navigateToShoppingCart() {
        _navigateAction.emit(ProductListNavigateAction.NavigateToShoppingCart)
    }

    override fun loadMoreProducts() {
        thread {
            productListPagingSource.load().onSuccess { pagingProduct ->
                hideError()
                _uiState.value?.let { state ->
                    val nowPagingProduct =
                        PagingProduct(
                            productList = state.pagingProduct.productList + pagingProduct.productList,
                            last = pagingProduct.last,
                        )
                    val cartCount = nowPagingProduct.productList.sumOf { it.quantity }

                    _uiState.postValue(
                        state.copy(
                            pagingProduct = nowPagingProduct,
                            cartCount = cartCount,
                        ),
                    )
                }
            }.onFailure { e ->
                showError(e)
                showMessage(MessageProvider.DefaultErrorMessage)
            }
        }
    }

    fun updateProducts() {
        thread {
            uiState.value?.let { state ->
                val cartProducts = shoppingCartRepository.getAllCartProducts().getOrNull().orEmpty()
                val updatedProductList =
                    state.pagingProduct.productList.map { product ->
                        val matchingCartProduct = cartProducts.find { it.id == product.id }
                        product.copy(quantity = matchingCartProduct?.quantity ?: 0)
                    }

                val productHistorys =
                    productHistoryRepository.getProductHistory(10).getOrDefault(emptyList())
                val updatedCartCount = updatedProductList.sumOf { it.quantity }

                _uiState.postValue(
                    state.copy(
                        pagingProduct = PagingProduct(updatedProductList),
                        productHistorys = productHistorys,
                        cartCount = updatedCartCount,
                    ),
                )
            }
        }
    }

    override fun plusProductQuantity(
        productId: Long,
        position: Int,
    ) {
        updateProductQuantity(productId, increment = true)
    }

    override fun minusProductQuantity(
        productId: Long,
        position: Int,
    ) {
        updateProductQuantity(productId, increment = false)
    }

    private fun updateProductQuantity(
        productId: Long,
        increment: Boolean,
    ) {
        _uiState.value?.let { state ->
            val updatedProductList =
                state.pagingProduct.productList.map { product ->
                    if (product.id == productId) {
                        product.updateProduct(increment)
                    } else {
                        product
                    }
                }
            val updatedCartCount = updatedProductList.sumOf { it.quantity }
            _uiState.postValue(
                state.copy(
                    pagingProduct = PagingProduct(updatedProductList),
                    cartCount = updatedCartCount,
                ),
            )
        }
    }

    private fun Product.updateProduct(increment: Boolean): Product {
        val updatedQuantity = if (increment) this.quantity + 1 else this.quantity - 1
        when {
            this.quantity == 0 -> insertCartProduct(this, updatedQuantity)
            updatedQuantity == 0 -> deleteCartProduct(this.id)
            else -> updateCartProduct(this.id, updatedQuantity)
        }
        return this.copy(quantity = updatedQuantity)
    }

    private fun insertCartProduct(
        product: Product,
        quantity: Int,
    ) {
        thread {
            shoppingCartRepository.insertCartProduct(
                productId = product.id,
                name = product.name,
                price = product.price,
                quantity = quantity,
                imageUrl = product.imageUrl,
            ).onFailure { e ->
                showError(e)
            }
        }
    }

    private fun deleteCartProduct(productId: Long) {
        thread {
            shoppingCartRepository.deleteCartProduct(
                productId = productId,
            ).onFailure { e ->
                showError(e)
            }
        }
    }

    private fun updateCartProduct(
        productId: Long,
        quantity: Int,
    ) {
        thread {
            shoppingCartRepository.updateCartProduct(
                productId = productId,
                quantity = quantity,
            ).onFailure { e ->
                showError(e)
            }
        }
    }

    companion object {
        fun factory(
            productRepository: ProductRepository,
            shoppingCartRepository: ShoppingCartRepository,
            productHistoryRepository: ProductHistoryRepository,
        ): ViewModelProvider.Factory {
            return BaseViewModelFactory {
                ProductListViewModel(
                    productRepository,
                    shoppingCartRepository,
                    productHistoryRepository,
                )
            }
        }
    }
}
