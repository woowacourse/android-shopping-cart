package woowacourse.shopping.presentation.ui.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.base.BaseViewModel
import woowacourse.shopping.presentation.base.MessageProvider
import woowacourse.shopping.presentation.ui.productdetail.ProductDetailActivity.Companion.PUT_EXTRA_PRODUCT_ID

class ProductDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : BaseViewModel() {
    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> get() = _product

    init {
        savedStateHandle.get<Int>(PUT_EXTRA_PRODUCT_ID)?.let { productId ->
            findByProductId(productId)
        }
    }

    private fun findByProductId(id: Int) {
        productRepository.findProductById(id).onSuccess { productValue ->
            _product.value = productValue
        }.onFailure { e ->
            when (e) {
                is NoSuchElementException -> showMessage(ProductDetailMessage.NoSuchElementErrorMessage)
                else -> showMessage(MessageProvider.DefaultErrorMessage)
            }
        }
    }

    fun onAddToCartButtonClick() {
        product.value?.let { product ->
            shoppingCartRepository.addOrder(product)
            showMessage(ProductDetailMessage.AddToCartSuccessMessage)
        }
    }
}
