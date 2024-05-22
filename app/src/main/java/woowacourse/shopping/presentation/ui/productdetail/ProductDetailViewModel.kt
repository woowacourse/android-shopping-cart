package woowacourse.shopping.presentation.ui.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.local.ProductHistoryRepository
import woowacourse.shopping.domain.repository.local.ShoppingCartRepository
import woowacourse.shopping.presentation.base.BaseViewModel
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.base.Event
import woowacourse.shopping.presentation.base.MessageProvider
import woowacourse.shopping.presentation.base.emit
import woowacourse.shopping.presentation.common.ProductCountHandler
import woowacourse.shopping.presentation.ui.productdetail.ProductDetailActivity.Companion.PUT_EXTRA_PRODUCT_ID
import woowacourse.shopping.presentation.ui.shoppingcart.UpdatedProducts
import kotlin.concurrent.thread

class ProductDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
    private val productHistoryRepository: ProductHistoryRepository,
) : BaseViewModel(), ProductCountHandler {
    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> get() = _product

    private val _isAddToCart: MutableLiveData<Boolean> = MutableLiveData()
    val isAddToCart: LiveData<Boolean> get() = _isAddToCart

    private val _navigateAction: MutableLiveData<Event<ProductDetailNavigateAction>> =
        MutableLiveData(null)
    val navigateAction: LiveData<Event<ProductDetailNavigateAction>> get() = _navigateAction

    init {
        savedStateHandle.get<Long>(PUT_EXTRA_PRODUCT_ID)?.let { productId ->
            findByProductId(productId)
        }
    }

    private fun findByProductId(id: Long) {
        productRepository.findProductById(id).onSuccess { productValue ->
            _product.value = productValue
            insertProductHistory(productValue)
            findProduct(id)
        }.onFailure { e ->
            when (e) {
                is NoSuchElementException -> showMessage(ProductDetailMessage.NoSuchElementErrorMessage)
                else -> showMessage(MessageProvider.DefaultErrorMessage)
            }
        }
    }

    private fun findProduct(productId: Long) {
        thread {
            shoppingCartRepository.findCartProduct(productId = productId)
                .onSuccess { productValue ->
                    _product.value?.let { value ->
                        _product.postValue(value.copy(quantity = productValue.quantity))
                    }
                }.onFailure {
                    // TODO 예외 처리
                }
        }
    }

    fun addToCart() {
        product.value?.let { product ->
            thread {
                shoppingCartRepository.insertCartProduct(
                    productId = product.id,
                    name = product.name,
                    price = product.price,
                    quantity = product.quantity,
                    imageUrl = product.imageUrl,
                ).onSuccess {
                    _isAddToCart.postValue(true)
                    showMessage(ProductDetailMessage.AddToCartSuccessMessage)
                }.onFailure {
                    showMessage(MessageProvider.DefaultErrorMessage)
                }
            }
        }
    }

    override fun addProductQuantity(
        productId: Long,
        position: Int,
    ) {
        _product.value?.let { value ->
            _product.value = value.copy(quantity = value.quantity + 1)
        }
    }

    override fun minusProductQuantity(
        productId: Long,
        position: Int,
    ) {
        _product.value?.let { value ->
            _product.value = value.copy(quantity = value.quantity - 1)
        }
    }

    fun navigateToProductList() {
        _product.value?.let { value ->
            if (isAddToCart.value == true) {
                val updatedProducts = UpdatedProducts(mutableMapOf(value.id to value))
                _navigateAction.emit(
                    ProductDetailNavigateAction.NavigateToProductList(
                        updatedProducts,
                    ),
                )
            } else {
                _navigateAction.emit(ProductDetailNavigateAction.NavigateToPrevious)
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
            ).onFailure { showMessage(MessageProvider.DefaultErrorMessage) }
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
