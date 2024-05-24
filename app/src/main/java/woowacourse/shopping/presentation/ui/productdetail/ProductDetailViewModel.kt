package woowacourse.shopping.presentation.ui.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.local.ProductHistoryRepository
import woowacourse.shopping.domain.repository.local.ShoppingCartRepository
import woowacourse.shopping.domain.repository.remote.ProductRepository
import woowacourse.shopping.presentation.base.BaseViewModel
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.base.Event
import woowacourse.shopping.presentation.base.MessageProvider
import woowacourse.shopping.presentation.base.emit
import woowacourse.shopping.presentation.common.ProductCountHandler
import woowacourse.shopping.presentation.ui.productdetail.ProductDetailActivity.Companion.PUT_EXTRA_PRODUCT_ID
import kotlin.concurrent.thread

class ProductDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
    private val productHistoryRepository: ProductHistoryRepository,
) : BaseViewModel(), ProductCountHandler {
    private val _uiState: MutableLiveData<ProductDetailUiState> =
        MutableLiveData(ProductDetailUiState())
    val uiState: LiveData<ProductDetailUiState> get() = _uiState

    private val _navigateAction: MutableLiveData<Event<ProductDetailNavigateAction>> =
        MutableLiveData(null)
    val navigateAction: LiveData<Event<ProductDetailNavigateAction>> get() = _navigateAction

    init {
        savedStateHandle.get<Long>(PUT_EXTRA_PRODUCT_ID)?.let { productId ->
            getProduct(productId)
        }
    }

    private fun getProduct(id: Long) {
        thread {
            productRepository.findProductById(id).mapCatching { product ->
                val carProduct = shoppingCartRepository.findCartProduct(id).getOrNull()
                carProduct ?: product.copy(quantity = 1)
            }.onSuccess { product ->
                _uiState.value?.let { state ->
                    if (state.isLastProductPage) {
                        _uiState.postValue(state.copy(product = product, isAddToCart = false))
                        insertProductHistory(product)
                    } else {
                        getProductHistory(product)
                    }
                }
            }
        }
    }

    fun addToCart() {
        _uiState.value?.let { state ->
            state.product?.let { product ->
                thread {
                    shoppingCartRepository.insertCartProduct(
                        productId = product.id,
                        name = product.name,
                        price = product.price,
                        quantity = product.quantity,
                        imageUrl = product.imageUrl,
                    ).onSuccess {
                        _uiState.postValue(state.copy(isAddToCart = true))
                        state.updatedProducts.addProduct(product.copy(quantity = product.quantity))
                        showMessage(ProductDetailMessage.AddToCartSuccessMessage)
                    }.onFailure {
                        showMessage(MessageProvider.DefaultErrorMessage)
                    }
                }
            }
        }
    }

    override fun addProductQuantity(
        productId: Long,
        position: Int,
    ) {
        _uiState.value?.let { state ->
            state.product?.let { product ->
                _uiState.value = state.copy(product = product.copy(quantity = product.quantity + 1))
            }
        }
    }

    override fun minusProductQuantity(
        productId: Long,
        position: Int,
    ) {
        _uiState.value?.let { state ->
            state.product?.let { product ->
                _uiState.value = state.copy(product = product.copy(quantity = product.quantity - 1))
            }
        }
    }

    fun navigateToProductList() {
        _uiState.value?.let { state ->
            state.product?.let { product ->
                if (state.isAddToCart) {
                    _navigateAction.emit(
                        ProductDetailNavigateAction.NavigateToProductList(
                            state.updatedProducts,
                        ),
                    )
                } else {
                    _navigateAction.emit(ProductDetailNavigateAction.NavigateToPrevious)
                }
            }
        }
    }

    private fun getProductHistory(product: Product) {
        productHistoryRepository.getProductHistory(2).onSuccess { productHistorys ->
            if (productHistorys.isEmpty()) {
                _uiState.postValue(
                    uiState.value?.copy(
                        product = product,
                        isAddToCart = false,
                        isLastProductPage = true,
                    ),
                )
                insertProductHistory(product)
                return@onSuccess
            }

            if (product.id == productHistorys.first().productId) {
                val productHistory =
                    if (productHistorys.size >= 2) {
                        productHistorys[1]
                    } else {
                        null
                    }

                _uiState.postValue(
                    uiState.value?.copy(
                        product = product,
                        productHistory = productHistory,
                        isAddToCart = false,
                        isLastProductPage = true,
                    ),
                )
            } else {
                _uiState.postValue(
                    uiState.value?.copy(
                        product = product,
                        productHistory = productHistorys.first(),
                        isAddToCart = false,
                    ),
                )
                insertProductHistory(product)
            }
        }.onFailure { showMessage(MessageProvider.DefaultErrorMessage) }
    }

    private fun insertProductHistory(productValue: Product) {
        thread {
            productHistoryRepository.insertProductHistory(
                productId = productValue.id,
                name = productValue.name,
                price = productValue.price,
                imageUrl = productValue.imageUrl,
            ).onFailure { showMessage(MessageProvider.DefaultErrorMessage) }
        }
    }

    fun refresh(productId: Long) {
        _uiState.value?.let { state ->
            _uiState.value = state.copy(isLastProductPage = true)
            getProduct(productId)
        }
    }

    companion object {
        fun factory(
            productRepository: ProductRepository,
            shoppingCartRepository: ShoppingCartRepository,
            productHistoryRepository: ProductHistoryRepository,
        ): ViewModelProvider.Factory {
            return BaseViewModelFactory { extras ->
                ProductDetailViewModel(
                    savedStateHandle = extras.createSavedStateHandle(),
                    productRepository = productRepository,
                    shoppingCartRepository = shoppingCartRepository,
                    productHistoryRepository = productHistoryRepository,
                )
            }
        }
    }
}
