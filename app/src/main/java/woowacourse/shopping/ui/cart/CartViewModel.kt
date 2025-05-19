package woowacourse.shopping.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.ShoppingApp
import woowacourse.shopping.domain.model.CartProducts
import woowacourse.shopping.domain.model.CartProducts.Companion.EMPTY_CART_PRODUCTS
import woowacourse.shopping.domain.repository.CartRepository

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _cartProducts: MutableLiveData<CartProducts> = MutableLiveData(EMPTY_CART_PRODUCTS)
    val cartProducts: LiveData<CartProducts> get() = _cartProducts

    private val _currentPage: MutableLiveData<Int> = MutableLiveData<Int>(INITIAL_PAGE)
    val currentPage: LiveData<Int> get() = _currentPage

    private val _maxPage: MutableLiveData<Int> = MutableLiveData<Int>(INITIAL_PAGE)
    val maxPage: LiveData<Int> get() = _maxPage

    init {
        loadCartProducts()
    }

    private fun loadCartProducts() {
        cartRepository.getCartProducts(page = currentPage.value ?: INITIAL_PAGE, size = 5) { cartProducts ->
            _cartProducts.postValue(cartProducts)
        }

        if (cartProducts.value?.products.isNullOrEmpty()) decreasePage()
    }

    fun removeCartProduct(id: Int) {
        cartRepository.removeCartProduct(id)
        loadCartProducts()
    }

    fun increasePage(step: Int = DEFAULT_PAGE_STEP) {
        _currentPage.value = currentPage.value?.plus(step)
        loadCartProducts()
    }

    fun decreasePage(step: Int = DEFAULT_PAGE_STEP) {
        if (currentPage.value == INITIAL_PAGE) return
        _currentPage.value = currentPage.value?.minus(step)
        loadCartProducts()
    }

    fun increaseCartProductQuantity(id: Int) {
        cartRepository.increaseProductQuantity(id) { newQuantity ->
            _cartProducts.postValue(
                cartProducts.value?.copy(
                    products = updateCartProductQuantity(id, newQuantity),
                ),
            )
        }
    }

    private fun updateCartProductQuantity(
        id: Int,
        newQuantity: Int,
    ) = _cartProducts.value?.products?.map { cartProduct ->
        if (cartProduct.product.id == id) {
            cartProduct.copy(quantity = newQuantity)
        } else {
            cartProduct
        }
    } ?: emptyList()

    fun decreaseCartProductQuantity(id: Int) {
        cartRepository.decreaseProductQuantity(id) { newQuantity ->
            _cartProducts.postValue(
                cartProducts.value?.copy(
                    products = updateCartProductQuantity(id, newQuantity),
                ),
            )
        }
    }

    companion object {
        const val INITIAL_PAGE: Int = 1
        const val DEFAULT_PAGE_STEP: Int = 1

        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    val application = checkNotNull(extras[APPLICATION_KEY])

                    return CartViewModel(
                        (application as ShoppingApp).cartRepository,
                    ) as T
                }
            }
    }
}
