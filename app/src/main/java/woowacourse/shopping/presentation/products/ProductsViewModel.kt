package woowacourse.shopping.presentation.products

import android.os.Handler
import android.os.Looper
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

    private val mainHandler = Handler(Looper.getMainLooper())


    init {
        cartRepository.fetchAllProduct { cartList ->
            val result = cartList.associate { it.product.id to it.count }

            mainHandler.post {
                _cart.value = result
            }
        }
        lastProductRepository.fetchProducts { products ->
            val result = products.map { it.product }
            mainHandler.post {
                _lastProducts.value = result
            }
        }
    }


    fun fetchLastProducts(){
        lastProductRepository.fetchProducts { products ->
            val result = products.map { it.product }
            mainHandler.post {
                _lastProducts.value = result
            }
        }

    }


    fun updateProducts(count: Int = SHOWN_PRODUCTS_COUNT) {
        productsRepository.fetchProducts(currentPage, count) { newProducts ->
            mainHandler.post {
                _products.value = products.value.orEmpty() + newProducts
                currentPage += 1
            }
        }
    }

    fun updateIsLoadable() {
        val lastId = products.value?.lastOrNull()?.id ?: 0
        productsRepository.fetchIsProductsLoadable(lastId) { result ->
            mainHandler.post {
                _isLoadingProducts.value = result
            }
        }
    }

    fun update(id: Int, count: Int) {
        val currentMap = _cart.value.orEmpty().toMutableMap()
        val currentCount = currentMap[id]

        if (currentCount == null) {
            currentMap[id] = count
        } else {
            currentMap[id] = currentCount + count
        }

        if ((currentMap[id] ?: 0) <= 0) {
            currentMap.remove(id)
        }

        _cart.value = currentMap
    }

    fun upCount(id: Int) {
        val product = _products.value?.find { it.id == id } ?: return

        update(id, 1)

        Thread {
            cartRepository.upsertCartProduct(product, 1)
        }.start()
    }

    fun downCount(id: Int) {
        val product = _products.value?.find { it.id == id } ?: return

        update(id, -1)

        Thread {
            cartRepository.upsertCartProduct(product, -1)
        }.start()
    }

    companion object {
        private const val SHOWN_PRODUCTS_COUNT = 20
    }
}