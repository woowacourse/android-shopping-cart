package woowacourse.shopping.ui.products.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.data.CartDao
import woowacourse.shopping.model.data.ProductDao

class ProductContentsViewModel(private val productDao: ProductDao, private val cartDao: CartDao) :
    ViewModel() {
    private val items = mutableListOf<Product>()

    private val _products: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>> get() = _products

    private val cart: MutableLiveData<List<Cart>> = MutableLiveData()

    val isCartEmpty: LiveData<Boolean> =
        cart.map {
            it.isEmpty()
        }

    val totalProductCount: LiveData<Int> =
        cart.map {
            if (it.isEmpty()) {
                DEFAULT_CART_ITEMS_COUNT
            } else {
                it.sumOf { cartItem -> cartItem.product.count }
            }
        }

    fun loadProducts() {
        items.addAll(productDao.getProducts())
        _products.value = items
    }

    fun loadCartItems() {
        cart.value = cartDao.findAll()
    }

    companion object {
        private const val DEFAULT_CART_ITEMS_COUNT = 0
    }
}
