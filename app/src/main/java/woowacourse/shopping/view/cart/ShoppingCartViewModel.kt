package woowacourse.shopping.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.ShoppingCartRepositoryImpl.Companion.DEFAULT_ITEM_SIZE
import woowacourse.shopping.data.repository.ShoppingCartRepositoryImpl.Companion.MIN_PAGE_COUNT
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
            _shoppingCartState.value = ShoppingCartState.DeleteShoppingCart.Success
            shoppingCart.deleteProduct(itemId)
            _shoppingCartState.value = ShoppingCartState.Init
        }.onFailure {
            _shoppingCartState.value = ShoppingCartState.DeleteShoppingCart.Fail
            _shoppingCartState.value = ShoppingCartState.Init
        }
    }

    fun loadPagingCartItemList(pagingSize: Int) {
        runCatching {
            val itemSize = shoppingCart.cartItems.value?.size ?: DEFAULT_ITEM_SIZE
            shoppingCartRepository.loadPagingCartItems(itemSize, pagingSize)
        }
            .onSuccess {
                _shoppingCartState.value = ShoppingCartState.LoadCartItemList.Success
                shoppingCart.addProducts(it)
                _shoppingCartState.value = ShoppingCartState.Init
            }
            .onFailure {
                _shoppingCartState.value = ShoppingCartState.LoadCartItemList.Fail
                _shoppingCartState.value = ShoppingCartState.Init
            }
    }
}
