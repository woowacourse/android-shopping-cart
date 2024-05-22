package woowacourse.shopping.ui.products.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.ProductWithQuantity
import woowacourse.shopping.model.Quantity
import woowacourse.shopping.model.data.CartDao
import woowacourse.shopping.model.data.ProductWithQuantityDao

class ProductContentsViewModel(
    private val productWithQuantityDao: ProductWithQuantityDao,
    private val cartDao: CartDao,
) :
    ViewModel() {
    private val items = mutableListOf<ProductWithQuantity>()

    private var _productWithQuantity: MutableLiveData<List<ProductWithQuantity>> = MutableLiveData()
    val productWithQuantity: LiveData<List<ProductWithQuantity>> get() = _productWithQuantity

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
                it.sumOf { cartItem -> cartItem.productWithQuantity.quantity.value }
            }
        }

    init {
        loadProducts()
    }

    fun loadProducts() {
        items.addAll(productWithQuantityDao.getProducts())
        _productWithQuantity.value = items
    }

    fun loadCartItems() {
        cart.value = cartDao.findAll()
    }

    fun plusCount(productId: Long) {
        val productIndex = items.indexOfFirst { it.product.id == productId }
        val newProduct = items[productIndex].copy(quantity = items[productIndex].quantity.inc())
        items[productIndex] = newProduct
        cartDao.save(Cart(productWithQuantity = newProduct.copy(quantity = Quantity(PLUS_OFFSET))))
        _productWithQuantity.value = items
        loadCartItems()
    }

    fun minusCount(productId: Long) {
        val productIndex = items.indexOfFirst { it.product.id == productId }
        val newProduct = items[productIndex].copy(quantity = items[productIndex].quantity.dec())
        items[productIndex] = newProduct
        cartDao.decreaseQuantity(
            Cart(
                productWithQuantity =
                    newProduct.copy(
                        quantity =
                            Quantity(
                                MINUS_OFFSET,
                            ),
                    ),
            ),
        )
        _productWithQuantity.value = items
        loadCartItems()
    }

    companion object {
        private const val DEFAULT_CART_ITEMS_COUNT = 0
        private const val PLUS_OFFSET = 1
        private const val MINUS_OFFSET = -1
    }
}
