package woowacourse.shopping.presentation.ui.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.local.ShoppingCartRepository
import woowacourse.shopping.presentation.base.BaseViewModel
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.base.MessageProvider
import woowacourse.shopping.presentation.ui.productdetail.ProductDetailActivity.Companion.PUT_EXTRA_PRODUCT_ID
import kotlin.concurrent.thread

class ProductDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : BaseViewModel() {
    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> get() = _product

    init {
        savedStateHandle.get<Long>(PUT_EXTRA_PRODUCT_ID)?.let { productId ->
            findByProductId(productId)
        }
    }

    private fun findByProductId(id: Long) {
        productRepository.findProductById(id).onSuccess { productValue ->
            _product.value = productValue
        }.onFailure { e ->
            when (e) {
                is NoSuchElementException -> showMessage(ProductDetailMessage.NoSuchElementErrorMessage)
                else -> showMessage(MessageProvider.DefaultErrorMessage)
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
                    quantity = 1,
                    imageUrl = product.imageUrl,
                ).onSuccess {
                    showMessage(ProductDetailMessage.AddToCartSuccessMessage)
                }.onFailure {
                    showMessage(MessageProvider.DefaultErrorMessage)
                }
            }
        }
    }

    companion object {
        fun factory(
            productRepository: ProductRepository,
            shoppingCartRepository: ShoppingCartRepository,
        ): ViewModelProvider.Factory {
            return BaseViewModelFactory { extras ->
                ProductDetailViewModel(
                    savedStateHandle = extras.createSavedStateHandle(),
                    productRepository = productRepository,
                    shoppingCartRepository = shoppingCartRepository,
                )
            }
        }
    }
}
