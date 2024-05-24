package woowacourse.shopping.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.ProductWithQuantity
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.presentation.ui.CartQuantityActionHandler

class DetailViewModel(
    val cartRepository: CartRepository,
    val shoppingRepository: ShoppingItemsRepository,
    val productId: Long,
) : ViewModel(), CartQuantityActionHandler {
    private val _productWithQuantity = MutableLiveData<ProductWithQuantity>()
    val productWithQuantity: LiveData<ProductWithQuantity> get() = _productWithQuantity

    private val _navigateToShoppingCart = MutableLiveData<Long>()
    val navigateToShoppingCart: LiveData<Long> get() = _navigateToShoppingCart

    init {
        loadProductData()
    }

    private fun loadProductData() {
        val product =
            shoppingRepository.productWithQuantityItem(productId)?.copy(
                quantity = shoppingRepository.productWithQuantityItem(productId)?.quantity?.takeIf { it > 0 } ?: 1,
            ) ?: return
        _productWithQuantity.postValue(product)
    }

    fun onAddToCartClicked(productId: Long) {
        createShoppingCartItem()
        _navigateToShoppingCart.postValue(productId)
    }

    private fun createShoppingCartItem() {
        productWithQuantity.value?.let {
            cartRepository.insert(
                productWithQuantity = it,
            )
        }
    }

    override fun onPlusButtonClicked(productId: Long) {
        productWithQuantity.value?.let {
            it.quantity.takeIf { quantity -> quantity < 100 } ?: return
            _productWithQuantity.postValue(it.copy(quantity = it.quantity + 1))
        }
    }

    override fun onMinusButtonClicked(productId: Long) {
        productWithQuantity.value?.let {
            it.quantity.takeIf { quantity -> quantity > 1 } ?: return
            _productWithQuantity.postValue(it.copy(quantity = it.quantity - 1))
        }
    }
}
