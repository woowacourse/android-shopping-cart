package woowacourse.shopping.presentation.ui.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.local.ShoppingCartRepository
import woowacourse.shopping.presentation.base.BaseViewModel
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.base.Event
import woowacourse.shopping.presentation.base.MessageProvider
import woowacourse.shopping.presentation.base.emit
import woowacourse.shopping.presentation.common.ProductCountHandler
import woowacourse.shopping.presentation.ui.shoppingcart.adapter.ShoppingCartPagingSource
import kotlin.concurrent.thread

class ShoppingCartViewModel(private val repository: ShoppingCartRepository) :
    BaseViewModel(),
    ShoppingCartActionHandler,
    ProductCountHandler {
    private val _uiState: MutableLiveData<ShoppingCartUiState> =
        MutableLiveData(ShoppingCartUiState())
    val uiState: LiveData<ShoppingCartUiState> get() = _uiState

    private val shoppingCartPagingSource = ShoppingCartPagingSource(repository)

    private val _navigateAction: MutableLiveData<Event<ShoppingCartNavigateAction>> =
        MutableLiveData(null)
    val navigateAction: LiveData<Event<ShoppingCartNavigateAction>> get() = _navigateAction

    init {
        loadCartProducts(INIT_PAGE)
    }

    private fun loadCartProducts(page: Int) {
        thread {
            shoppingCartPagingSource.load(page).onSuccess { pagingCartProduct ->
                _uiState.postValue(_uiState.value?.copy(pagingCartProduct = pagingCartProduct))
            }.onFailure {
                showMessage(MessageProvider.DefaultErrorMessage)
            }
        }
    }

    override fun addProductQuantity(
        productId: Long,
        position: Int,
    ) {
        _uiState.value?.let { state ->
            val newProductList =
                state.pagingCartProduct.products.map { product ->
                    if (product.id == productId) {
                        insertCartProduct(product)
                        state.updatedProducts.addProduct(product.copy(quantity = product.quantity + 1))
                        product.copy(quantity = product.quantity + 1)
                    } else {
                        product
                    }
                }

            val pagingCartProduct =
                PagingCartProduct(products = newProductList, last = state.pagingCartProduct.last)
            _uiState.value = state.copy(pagingCartProduct = pagingCartProduct)
        }
    }

    private fun insertCartProduct(product: Product) {
        thread {
            repository.insertCartProduct(
                productId = product.id,
                name = product.name,
                price = product.price,
                quantity = product.quantity + 1,
                imageUrl = product.imageUrl,
            ).onFailure {
                // TODO 예외처리
            }
        }
    }

    override fun minusProductQuantity(
        productId: Long,
        position: Int,
    ) {
        _uiState.value?.let { state ->
            val newProductList =
                state.pagingCartProduct.products.map { product ->
                    if (product.id == productId) {
                        if (product.quantity == 1) {
                            deleteCartProduct(productId)
                        } else {
                            updateCartProduct(productId, product.quantity - 1)
                        }
                        state.updatedProducts.addProduct(product.copy(quantity = product.quantity - 1))
                        product.copy(quantity = product.quantity - 1)
                    } else {
                        product
                    }
                }

            val pagingCartProduct =
                PagingCartProduct(products = newProductList, last = state.pagingCartProduct.last)
            _uiState.value = state.copy(pagingCartProduct = pagingCartProduct)
        }
    }

    override fun deleteCartProduct(productId: Long) {
        thread {
            repository.deleteCartProduct(productId = productId).onSuccess {
                uiState.value?.let { state ->
                    val product =
                        state.pagingCartProduct.products.find { it.id == productId }
                            ?: throw NoSuchElementException()
                    state.updatedProducts.addProduct(product.copy(quantity = 0))
                    loadCartProducts(state.pagingCartProduct.currentPage)
                }
            }.onFailure {
                showMessage(MessageProvider.DefaultErrorMessage)
            }
        }
    }

    private fun updateCartProduct(
        productId: Long,
        quantity: Int,
    ) {
        thread {
            repository.updateCartProduct(
                productId = productId,
                quantity = quantity,
            ).onFailure {
                // TODO 예외처리
            }
        }
    }

    fun loadNextPage() {
        uiState.value?.let { state ->
            loadCartProducts(state.pagingCartProduct.currentPage + 1)
        }
    }

    fun loadPreviousPage() {
        uiState.value?.let { state ->
            loadCartProducts(state.pagingCartProduct.currentPage - 1)
        }
    }

    fun navigateToProductList() {
        uiState.value?.let { state ->
            _navigateAction.emit(ShoppingCartNavigateAction.NavigateToProductList(state.updatedProducts))
        }
    }

    companion object {
        const val INIT_PAGE = 0

        fun factory(repository: ShoppingCartRepository): ViewModelProvider.Factory {
            return BaseViewModelFactory { ShoppingCartViewModel(repository) }
        }
    }
}
