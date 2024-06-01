package woowacourse.shopping.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.model.cart.CartedProduct
import woowacourse.shopping.domain.repository.cart.CartRepository
import woowacourse.shopping.domain.repository.product.ProductRepository
import woowacourse.shopping.presentation.home.products.ProductQuantity
import woowacourse.shopping.presentation.home.products.QuantityListener
import java.util.Collections.replaceAll

class CartViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : ViewModel(), CartItemEventListener, QuantityListener {
    private var _currentPage: MutableLiveData<Int> = MutableLiveData(0)
    val currentPage: LiveData<Int>
        get() = _currentPage

    private val _cartableProducts: MutableLiveData<List<CartedProduct>> =
        MutableLiveData(emptyList())
    val cartableProducts: LiveData<List<CartedProduct>>
        get() = _cartableProducts

    private val _pageInformation: MutableLiveData<PageInformation> =
        MutableLiveData(PageInformation())
    val pageInformation: LiveData<PageInformation>
        get() = _pageInformation

    var alteredCartItems: Array<Long> = arrayOf()
        private set

    private var hasNext: Boolean = true

    init {
        loadCurrentPageCartItems()
    }

    fun loadPreviousPageCartItems() {
        _currentPage.value = currentPage.value?.minus(1)
        loadCurrentPageCartItems()
    }

    fun loadNextPageCartItems() {
        _currentPage.value = currentPage.value?.plus(1)
        loadCurrentPageCartItems()
    }

    fun loadCurrentPageCartItems() {
        val cartItems = cartRepository.fetchCartItems(currentPage.value ?: return)
        hasNext =
            cartRepository.fetchCartItems(currentPage.value?.plus(1) ?: return)
                .isNotEmpty()
        setPageInformation()
        _cartableProducts.value = cartItems
    }

    override fun onCartItemDelete(cartedProduct: CartedProduct) {
        cartRepository.patchQuantity(cartedProduct.product.id, 0, cartedProduct.cartItem)
        alteredCartItems += cartedProduct.product.id
        if (cartableProducts.value?.size == 1 && currentPage.value != 0) {
            _currentPage.value = currentPage.value?.minus(1)
        }
        loadCurrentPageCartItems()
    }

    override fun onQuantityChange(
        productId: Long,
        quantity: Int,
    ) {
        if (quantity < 0) return
        val targetItem = productRepository.fetchProduct(productId)
        if (targetItem.cartItem != null) {
            cartRepository.patchQuantity(productId, quantity, targetItem.cartItem)
            if (productId !in alteredCartItems) {
                alteredCartItems += productId
            }
        }
        loadCurrentPageCartItems()
    }

    private fun setPageInformation() {
        if (currentPage.value == 0) {
            _pageInformation.value =
                pageInformation.value?.copy(
                    previousPageEnabled = false,
                    nextPageEnabled = hasNext,
                )
        } else {
            _pageInformation.value =
                pageInformation.value?.copy(
                    previousPageEnabled = true,
                    nextPageEnabled = hasNext,
                )
        }
    }
}
