package woowacourse.shopping.ui.products.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductWithQuantity
import woowacourse.shopping.model.Quantity
import woowacourse.shopping.model.data.ProductDao
import woowacourse.shopping.model.data.ProductsImpl
import woowacourse.shopping.model.db.Cart
import woowacourse.shopping.model.db.CartDao
import woowacourse.shopping.model.db.recentproduct.RecentProduct
import woowacourse.shopping.model.db.recentproduct.RecentProductRepository

class ProductContentsViewModel(
    private val productDao: ProductDao,
    private val recentProductRepository: RecentProductRepository,
    private val cartDao: CartDao,
) :
    ViewModel() {
    private val items = mutableListOf<Product>()
    private val products: MutableLiveData<List<Product>> = MutableLiveData()

    private val cart: MutableLiveData<List<Cart>> = MutableLiveData()

    val productWithQuantity: MediatorLiveData<List<ProductWithQuantity>> = MediatorLiveData()

    val isCartEmpty: LiveData<Boolean> =
        cart.map {
            it.isEmpty()
        }

    val totalProductCount: LiveData<Int> =
        cart.map {
            if (it.isEmpty()) {
                DEFAULT_CART_ITEMS_COUNT
            } else {
                it.sumOf { cartItem -> cartItem.quantity.value }
            }
        }

    private val _recentProducts: MutableLiveData<List<RecentProduct>> = MutableLiveData()
    val recentProducts: LiveData<List<Product>> =
        _recentProducts.map { recentProducts ->
            recentProducts.map { ProductsImpl.find(it.productId) }
        }

    init {
        productWithQuantity.addSource(products) { updateProductWithQuantity() }
        productWithQuantity.addSource(cart) { updateProductWithQuantity() }
        loadProducts()
    }

    fun loadProducts() {
        items.addAll(productDao.getProducts())
        products.value = items
    }

    fun loadCartItems() {
        cart.value = cartDao.findAll()
    }

    fun plusCount(productId: Long) {
        cartDao.plusQuantityByProductId(productId)
        loadCartItems()
    }

    fun minusCount(productId: Long) {
        cartDao.minusQuantityByProductId(productId)
        loadCartItems()
    }

    fun loadRecentProducts() {
        Thread {
            _recentProducts.postValue(recentProductRepository.findAll())
        }.start()
    }

    private fun updateProductWithQuantity() {
        val currentProducts = products.value ?: emptyList()
        val updatedList =
            currentProducts.map { product ->
                ProductWithQuantity(product = product, quantity = getQuantity(product.id))
            }
        productWithQuantity.value = updatedList
    }

    private fun getQuantity(productId: Long): Quantity {
        val cart = findCartContainProduct(productId) ?: return Quantity()
        return cart.quantity
    }

    private fun findCartContainProduct(productId: Long): Cart? {
        cart.value?.let { items ->
            return items.find { it.productId == productId }
        }
        return null
    }

    companion object {
        private const val DEFAULT_CART_ITEMS_COUNT = 0
    }
}
