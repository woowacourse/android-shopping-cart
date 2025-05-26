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
import woowacourse.shopping.domain.usecase.DecreaseCartProductQuantityUseCase
import woowacourse.shopping.domain.usecase.GetCartProductsUseCase
import woowacourse.shopping.domain.usecase.IncreaseCartProductQuantityUseCase
import woowacourse.shopping.domain.usecase.RemoveCartProductUseCase
import woowacourse.shopping.ui.model.PageState
import woowacourse.shopping.ui.model.PageState.Companion.INITIAL_PAGE

class CartViewModel(
    private val getCartProductsUseCase: GetCartProductsUseCase,
    private val removeCartProductUseCase: RemoveCartProductUseCase,
    private val increaseCartProductQuantityUseCase: IncreaseCartProductQuantityUseCase,
    private val decreaseCartProductQuantityUseCase: DecreaseCartProductQuantityUseCase,
) : ViewModel() {
    private val _cartProducts: MutableLiveData<CartProducts> = MutableLiveData(EMPTY_CART_PRODUCTS)
    val cartProducts: LiveData<CartProducts> get() = _cartProducts

    private val _pageState: MutableLiveData<PageState> = MutableLiveData<PageState>(PageState())
    val pageState: LiveData<PageState> get() = _pageState

    private val _editedProductIds: MutableLiveData<Set<Int>> = MutableLiveData(emptySet())
    val editedProductIds: LiveData<Set<Int>> get() = _editedProductIds

    init {
        loadCartProducts()
    }

    private fun loadCartProducts() {
        getCartProductsUseCase(
            page = pageState.value?.current ?: INITIAL_PAGE,
            size = DEFAULT_PAGE_SIZE,
        ) { cartProducts ->
            _cartProducts.postValue(cartProducts)
        }

        if (cartProducts.value?.products.isNullOrEmpty()) {
            decreasePage()
        }
    }

    fun removeCartProduct(id: Int) {
        removeCartProductUseCase(id)
        _editedProductIds.postValue(editedProductIds.value?.plus(id))
        loadCartProducts()
    }

    fun increasePage(step: Int = DEFAULT_PAGE_STEP) {
        _pageState.value = pageState.value?.plus(step)
        loadCartProducts()
    }

    fun decreasePage(step: Int = DEFAULT_PAGE_STEP) {
        if (pageState.value?.isFirstPage ?: true) return
        _pageState.value = pageState.value?.minus(step)
        loadCartProducts()
    }

    fun increaseCartProductQuantity(id: Int) {
        increaseCartProductQuantityUseCase(id) { newQuantity ->
            _cartProducts.postValue(cartProducts.value?.updateCartProductQuantity(id, newQuantity))
        }
        _editedProductIds.value = editedProductIds.value?.plus(id)
    }

    fun decreaseCartProductQuantity(id: Int) {
        decreaseCartProductQuantityUseCase(id) { newQuantity ->
            if (newQuantity > 0) {
                _cartProducts.postValue(cartProducts.value?.updateCartProductQuantity(id, newQuantity))
            } else {
                loadCartProducts()
            }
        }
        _editedProductIds.value = editedProductIds.value?.plus(id)
    }

    fun updateTotalPage(total: Int) {
        _pageState.value = pageState.value?.copy(total = total)
    }

    companion object {
        const val DEFAULT_PAGE_STEP: Int = 1
        const val DEFAULT_PAGE_SIZE: Int = 5

        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    val application = checkNotNull(extras[APPLICATION_KEY]) as ShoppingApp

                    return CartViewModel(
                        getCartProductsUseCase = application.getCartProductsUseCase,
                        removeCartProductUseCase = application.removeCartProductUseCase,
                        increaseCartProductQuantityUseCase = application.increaseCartProductQuantityUseCase,
                        decreaseCartProductQuantityUseCase = application.decreaseCartProductQuantityUseCase,
                    ) as T
                }
            }
    }
}
