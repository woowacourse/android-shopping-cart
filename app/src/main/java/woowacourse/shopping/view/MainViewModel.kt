package woowacourse.shopping.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ShoppingCart
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.concurrent.thread

class MainViewModel(
    private val repository: ProductRepository,
) : ViewModel() {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val products: LiveData<List<Product>> get() = _products

    var shoppingCart = ShoppingCart()


    init {
        loadPagingProduct()
    }

    fun loadPagingProduct() {
        val pagingData = repository.loadPagingProducts(products.value?.size ?: 0)
        if (pagingData.isNotEmpty()) {
            _products.value = _products.value?.plus(pagingData)
        }
    }

    fun addShoppingCartItem(product: Product) = thread {
        repository.addCartItem(product)
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

    fun loadPagingCartItem() {
        var pagingData = emptyList<CartItem>()
        thread {
            pagingData = repository.loadPagingCartItems(shoppingCart.cartItems.value?.size ?: 0)
        }.join()
        if (pagingData.isNotEmpty()) {
            shoppingCart.addProducts(pagingData)
        }
    }
}
