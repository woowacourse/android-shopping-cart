package woowacourse.shopping.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ShoppingCart
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.utils.NoSuchDataException
import kotlin.concurrent.thread

class MainViewModel(
    private val repository: ProductRepository,
) : ViewModel() {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val products: LiveData<List<Product>> get() = _products

    private val _shoppingCart: MutableLiveData<ShoppingCart> = MutableLiveData(ShoppingCart())
    val shoppingCart: LiveData<ShoppingCart> get() = _shoppingCart


    init {
        initializeProducts()
    }

    private fun initializeProducts() {
        _products.value = repository.loadProducts()
    }

    fun loadShoppingCart() = thread {
        val cartItems = repository.loadCartItems()
        _shoppingCart.postValue(ShoppingCart.makeShoppingCart(cartItems))
    }.join()

    fun addShoppingCartItem(product: Product) {
        var newCartItem: CartItem? = null
        thread {
            newCartItem = repository.addCartItem(product)
        }.join()
        _shoppingCart.value?.addProduct(newCartItem ?: throw NoSuchDataException())
    }

    fun deleteShoppingCartItem(itemId: Long) {
        thread {
            repository.deleteCartItem(itemId)
        }.join()
        _shoppingCart.value?.deleteProduct(itemId)
    }

    fun loadProductItem(productId: Long): Product {
        return repository.getProduct(productId)
    }

}
