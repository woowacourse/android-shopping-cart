package woowacourse.shopping.ui.products.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.data.cart.Cart
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.recentproduct.RecentProduct
import woowacourse.shopping.data.recentproduct.RecentProductRepository
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductWithQuantity
import woowacourse.shopping.model.Quantity
import kotlin.concurrent.thread

class ProductContentsViewModel(
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository,
    private val cartRepository: CartRepository,
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
            products(recentProducts)
        }

    private fun products(recentProducts: List<RecentProduct>): List<Product> {
        lateinit var products: List<Product>
        thread {
            products = recentProducts.map { productRepository.find(it.productId) }
        }.join()
        return products
    }

    init {
        productWithQuantity.addSource(products) { updateProductWithQuantity() }
        productWithQuantity.addSource(cart) { updateProductWithQuantity() }
        loadProducts()
    }

    fun loadProducts() {
        thread {
            items.addAll(productRepository.getProducts())
            products.postValue(items)
        }.join()
    }

    fun loadCartItems() {
        thread {
            cart.postValue(cartRepository.findAll())
        }.join()
    }

    fun plusCount(productId: Long) {
        thread {
            cartRepository.plusQuantityByProductId(productId)
        }.join()
        loadCartItems()
    }

    fun minusCount(productId: Long) {
        thread {
            cartRepository.minusQuantityByProductId(productId)
        }.join()
        loadCartItems()
    }

    fun loadRecentProducts() {
        thread {
            _recentProducts.postValue(recentProductRepository.findAll())
        }.join()
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
