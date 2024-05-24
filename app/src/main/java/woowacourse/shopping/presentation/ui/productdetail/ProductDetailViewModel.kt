package woowacourse.shopping.presentation.ui.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductListRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.base.BaseViewModel
import woowacourse.shopping.presentation.base.MessageProvider
import woowacourse.shopping.presentation.ui.productdetail.ProductDetailActivity.Companion.PUT_EXTRA_PRODUCT_ID

class ProductDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val productListRepository: ProductListRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : BaseViewModel(), ProductDetailActionHandler {
    private val _quantity: MutableLiveData<Int> = MutableLiveData()
    val quantity: LiveData<Int> get() = _quantity

    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> get() = _product

    init {
        savedStateHandle.get<Int>(PUT_EXTRA_PRODUCT_ID)?.let { productId ->
            findByProductId(productId)
        }
    }

    private fun findByProductId(id: Int) {
        productListRepository.findProductById(id).onSuccess { productValue ->
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
            shoppingCartRepository.plusOrder(product)
            showMessage(ProductDetailMessage.AddToCartSuccessMessage)
        }
    }

    override fun onClickPlusOrderButton() {
        product.value?.let { product ->
            shoppingCartRepository.plusOrder(product)
            getQuantity(product)
        }
    }

    override fun onClickMinusOrderButton() {
        product.value?.let { product ->
            shoppingCartRepository.minusOrder(product)
            getQuantity(product)
        }
    }

    private fun getQuantity(product: Product) {
        val quantity = shoppingCartRepository.getOrderByProductId(product.id)?.quantity ?: -1
        _quantity.value = quantity
    }
}
