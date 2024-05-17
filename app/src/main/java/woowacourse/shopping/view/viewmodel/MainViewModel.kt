package woowacourse.shopping.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.utils.NoSuchDataException
import woowacourse.shopping.view.cart.ShoppingCartFragment.Companion.DEFAULT_ITEM_SIZE
import woowacourse.shopping.view.cart.model.ShoppingCart
import kotlin.concurrent.thread

class MainViewModel(
    private val repository: ProductRepository,
) : ViewModel() {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val products: LiveData<List<Product>> get() = _products

    var shoppingCart = ShoppingCart()

    fun loadPagingProduct(pagingSize: Int) {
        val itemSize = products.value?.size ?: DEFAULT_ITEM_SIZE
        val pagingData = repository.loadPagingProducts(itemSize, pagingSize)
        if (pagingData.isEmpty()) throw NoSuchDataException()
        _products.value = _products.value?.plus(pagingData)
    }

    fun addShoppingCartItem(product: Product) =
        thread {
            val addedCartItemId = repository.addCartItem(product)
            if (addedCartItemId == ERROR_SAVE_DATA_ID) throw NoSuchDataException()
        }

    fun deleteShoppingCartItem(itemId: Long) {
        thread {
            repository.deleteCartItem(itemId)
        }.join()
        shoppingCart.deleteProduct(itemId)
    }

    fun loadProductItem(productId: Long): Product {
        return repository.getProduct(productId)
    }

    fun loadPagingCartItem(pagingSize: Int) {
        var pagingData = emptyList<CartItem>()
        thread {
            val itemSize = shoppingCart.cartItems.value?.size ?: DEFAULT_ITEM_SIZE
            pagingData = repository.loadPagingCartItems(itemSize, pagingSize)
        }.join()
        if (pagingData.isNotEmpty()) {
            shoppingCart.addProducts(pagingData)
        }
    }

    companion object {
        const val ERROR_SAVE_DATA_ID = -1L
    }
}
