package woowacourse.shopping.presentation.products

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.data.repository.LastProductRepository
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.domain.model.Product

class ProductsViewModel(
    private val productsRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val lastProductRepository: LastProductRepository
) : ViewModel() {
    private var currentPage = 1
    private val _products = MutableLiveData<List<Product>>(emptyList())
    val products: LiveData<List<Product>> get() = _products

    private val _isLoadingProducts = MutableLiveData<Boolean>(false)
    val isLoadingProducts: LiveData<Boolean> get() = _isLoadingProducts

    private val _cart = MutableLiveData<Map<Int, Int>>(emptyMap())
    val cart: LiveData<Map<Int, Int>> get() = _cart

    private var _lastProducts = MutableLiveData<List<Product>>(emptyList())
    val lastProducts: LiveData<List<Product>> get() = _lastProducts

    init {
        fetchCartProducts()
        fetchLastProducts()
    }

    fun fetchCartProducts() {
        cartRepository.fetchAllProduct { cartList ->
            val result = cartList.associate { it.product.id to it.count }

            _cart.postValue(result)

        }
    }


    fun fetchLastProducts() {
        lastProductRepository.fetchProducts { products ->
            val result = products.map { it.product }
            _lastProducts.postValue(result)
            Log.d("test",_lastProducts.value.toString())
        }

    }


    fun updateProducts(count: Int = SHOWN_PRODUCTS_COUNT) {
        productsRepository.fetchProducts(currentPage, count) { newProducts ->
            _products.postValue(products.value.orEmpty() + newProducts)
            currentPage += 1
            updateIsLoadable()
        }
    }

    fun updateIsLoadable() {
        val lastId = products.value?.lastOrNull()?.id ?: 0
        productsRepository.fetchIsProductsLoadable(lastId) { result ->
            _isLoadingProducts.postValue(result)
        }
    }

    private fun updateCountOrRemoveIfZero(id: Int, count: Int) {
        val currentCart = _cart.value.orEmpty().toMutableMap()
        val currentCount = currentCart[id]

        if (currentCount == null) {
            currentCart[id] = count
        } else {
            currentCart[id] = currentCount + count
        }

        if ((currentCart[id] ?: 0) <= 0) {
            currentCart.remove(id)
        }

        _cart.postValue(currentCart)
    }

    fun upCount(id: Int) {
        val product = _products.value?.find { it.id == id } ?: return
        updateCountOrRemoveIfZero(id, 1)
        cartRepository.upsertCartProduct(product, 1)
    }

    fun downCount(id: Int) {
        val product = _products.value?.find { it.id == id } ?: return

        updateCountOrRemoveIfZero(id, -1)

        cartRepository.upsertCartProduct(product, -1)
    }

    companion object {
        private const val SHOWN_PRODUCTS_COUNT = 20
    }
}