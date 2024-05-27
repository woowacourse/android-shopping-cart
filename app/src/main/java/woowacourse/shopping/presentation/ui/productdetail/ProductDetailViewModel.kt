package woowacourse.shopping.presentation.ui.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductHistoryRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.base.BaseViewModel
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.base.MessageProvider
import woowacourse.shopping.presentation.common.ProductCountHandler
import woowacourse.shopping.presentation.ui.productdetail.ProductDetailActivity.Companion.PUT_EXTRA_PRODUCT_ID
import kotlin.concurrent.thread

class ProductDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
    private val productHistoryRepository: ProductHistoryRepository,
) : BaseViewModel(), ProductCountHandler {
    private var id = requireNotNull(savedStateHandle.get<Long>(PUT_EXTRA_PRODUCT_ID))

    private val _uiState: MutableLiveData<ProductDetailUiState> =
        MutableLiveData(ProductDetailUiState())
    val uiState: LiveData<ProductDetailUiState> get() = _uiState

    init {
        getProduct()
    }

    fun getProduct() {
        thread {
            productRepository.findProductById(id).onSuccess { product ->
                hideError()
                _uiState.value?.let { state ->
                    if (state.isLastProductPage) {
                        _uiState.postValue(state.copy(product = product))
                        insertProductHistory(product)
                    } else {
                        getProductHistory(product)
                    }
                }
            }.onFailure { e ->
                showError(e)
            }
        }
    }

    override fun retry() {
        getProduct()
    }

    private fun getProductHistory(product: Product) {
        productHistoryRepository.getProductHistory(2).onSuccess { productHistorys ->
            hideError()
            val productHistory =
                if (productHistorys.isNotEmpty() && product.id == productHistorys.first().id) {
                    if (productHistorys.size >= 2) productHistorys[1] else null
                } else {
                    productHistorys.firstOrNull()
                }

            val isLastProductPage =
                when {
                    productHistorys.isEmpty() -> true
                    product.id == productHistorys.first().id -> productHistorys.size < 2
                    else -> false
                }

            uiState.value?.let { state ->
                _uiState.postValue(
                    state.copy(
                        product = product,
                        productHistory = productHistory,
                        isLastProductPage = isLastProductPage,
                    ),
                )
            }

            insertProductHistory(product)
        }.onFailure { e ->
            showError(e)
            showMessage(MessageProvider.DefaultErrorMessage)
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
                        hideError()
                        showMessage(ProductDetailMessage.AddToCartSuccessMessage)
                    }.onFailure { e ->
                        showError(e)
                        showMessage(MessageProvider.DefaultErrorMessage)
                    }
                }
            }
        }
    }

    override fun plusProductQuantity(
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

    private fun insertProductHistory(productValue: Product) {
        thread {
            productHistoryRepository.insertProductHistory(
                productId = productValue.id,
                name = productValue.name,
                price = productValue.price,
                imageUrl = productValue.imageUrl,
            ).onSuccess {
                hideError()
            }.onFailure { e ->
                showError(e)
                showMessage(MessageProvider.DefaultErrorMessage)
            }
        }
    }

    fun refresh(productId: Long) {
        _uiState.value?.let { state ->
            _uiState.value = state.copy(isLastProductPage = true)
            id = productId
            getProduct()
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
