package woowacourse.shopping.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.view.cart.model.ShoppingCart

class ShoppingCartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    var shoppingCart = ShoppingCart()
    var currentPage = MIN_PAGE_COUNT
    val totalItemSize: Int get() = shoppingCart.cartItems.value?.size ?: DEFAULT_ITEM_SIZE

    private val _shoppingCartState: MutableLiveData<ShoppingCartState> =
        MutableLiveData(ShoppingCartState.Init)
    val shoppingCartState: LiveData<ShoppingCartState> get() = _shoppingCartState!!

    fun deleteShoppingCartItem(itemId: Long) {
        runCatching {
            shoppingCartRepository.deleteCartItem(itemId)
        }.onSuccess {
            shoppingCart.deleteProduct(itemId)
            _shoppingCartState.value = ShoppingCartState.DeleteShoppingCart.Success
        }.onFailure {
            _shoppingCartState.value = ShoppingCartState.DeleteShoppingCart.Fail
        }
    }

    fun loadPagingCartItemList(pagingSize: Int) {
        runCatching {
            val itemSize = shoppingCart.cartItems.value?.size ?: DEFAULT_ITEM_SIZE
            shoppingCartRepository.loadPagingCartItems(itemSize, pagingSize)
        }
            .onSuccess {
                shoppingCart.addProducts(it)
                _shoppingCartState.value = ShoppingCartState.LoadCartItemList.Success
            }
            .onFailure {
                _shoppingCartState.value = ShoppingCartState.LoadCartItemList.Fail
            }
    }

    companion object {
        private const val DEFAULT_ITEM_SIZE = 0
        const val MIN_PAGE_COUNT = 1
    }

}
