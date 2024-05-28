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
import kotlin.concurrent.thread

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

    var alteredCartItems: ArrayList<ProductQuantity> = arrayListOf()
        private set

    private var hasNext: Boolean = true

    init {
        thread {
            loadCurrentPageCartItems()
        }
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
        thread {
            val cartItems = cartRepository.fetchCartItems(currentPage.value ?: return@thread)
            hasNext =
                cartRepository.fetchCartItems(currentPage.value?.plus(1) ?: return@thread)
                    .isNotEmpty()
            setPageInformation()
            _cartableProducts.postValue(cartItems)
        }
    }

    override fun onCartItemDelete(cartedProduct: CartedProduct) {
        thread {
            cartRepository.removeCartItem(cartedProduct.cartItem)
            alteredCartItems.add(ProductQuantity(cartedProduct.product.id, 0))
            if (cartableProducts.value?.size == 1 && currentPage.value != 0) {
                _currentPage.postValue(currentPage.value?.minus(1))
            }
            loadCurrentPageCartItems()
        }
    }

    override fun onQuantityChange(
        productId: Long,
        quantity: Int,
    ) {
        if (quantity < 0) return
        thread {
            val targetItem = productRepository.fetchProduct(productId)
            if (targetItem.cartItem != null) {
                if (quantity == 0) {
                    cartRepository.removeCartItem(targetItem.cartItem)
                } else {
                    cartRepository.updateQuantity(targetItem.cartItem.id ?: return@thread, quantity)
                }
                if (productId !in alteredCartItems.map(ProductQuantity::productId)) {
                    alteredCartItems.add(ProductQuantity(productId, quantity))
                } else {
                    replaceAll(
                        alteredCartItems,
                        alteredCartItems.first { it.productId == productId },
                        ProductQuantity(productId, quantity),
                    )
                }
            }
            loadCurrentPageCartItems()
        }
    }

    private fun setPageInformation() {
        if (currentPage.value == 0) {
            _pageInformation.postValue(
                pageInformation.value?.copy(
                    previousPageEnabled = false,
                    nextPageEnabled = hasNext,
                ),
            )
        } else {
            _pageInformation.postValue(
                pageInformation.value?.copy(
                    previousPageEnabled = true,
                    nextPageEnabled = hasNext,
                ),
            )
        }
    }
}
