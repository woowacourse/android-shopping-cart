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

    private val _editedProductIds: MutableLiveData<List<Int>> = MutableLiveData(emptyList())
    val editedProductIds: LiveData<List<Int>> get() = _editedProductIds

    init {
        loadCartProducts()
    }

    private fun loadCartProducts() {
        cartRepository.fetchCartProducts(
            page = currentPage.value ?: INITIAL_PAGE,
            size = DEFAULT_PAGE_SIZE,
        ) { cartProducts ->
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
                cartProducts.value?.updateCartProductQuantity(id, newQuantity),
            )
        }
        _editedProductIds.value = editedProductIds.value?.plus(id)
    }

    fun decreaseCartProductQuantity(id: Int) {
        cartRepository.decreaseProductQuantity(id) { newQuantity ->
            _cartProducts.postValue(
                cartProducts.value?.updateCartProductQuantity(id, newQuantity),
            )
        }
        _editedProductIds.value = editedProductIds.value?.plus(id)
    }

    companion object {
        const val INITIAL_PAGE: Int = 1
        const val DEFAULT_PAGE_STEP: Int = 1
        const val DEFAULT_PAGE_SIZE: Int = 5

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
