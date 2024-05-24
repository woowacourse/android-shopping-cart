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
    private val _price: MutableLiveData<Int> = MutableLiveData()
    val price: LiveData<Int> get() = _price

    private val _quantity: MutableLiveData<Int> = MutableLiveData(1)
    val quantity: LiveData<Int> get() = _quantity

    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> get() = _product

    init {
        savedStateHandle.get<Int>(PUT_EXTRA_PRODUCT_ID)?.let(::findByProductId)
        getPrice()
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
            quantity.value?.let { quantity ->
                shoppingCartRepository.plusOrder(product, quantity)
            }
            showMessage(ProductDetailMessage.AddToCartSuccessMessage)
        }
    }

    override fun onClickPlusOrderButton() {
        _quantity.value = _quantity.value?.plus(1)
    }

    override fun onClickMinusOrderButton() {
        quantity.value?.let { value ->
            if (value > 1) _quantity.value = value - 1
        }
    }

    private fun getQuantity(product: Product) {
        val quantity = shoppingCartRepository.getOrderByProductId(product.id)?.quantity ?: 1
        _quantity.value = quantity
    }

    private fun getPrice() {
        product.value?.price?.let {
            _price.value = quantity.value?.times(it)
        }
    }
}
