package woowacourse.shopping.presentation.viewmodel.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.CartDummyRepositoryImpl
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.data.repository.ProductDummyRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.domain.model.Product

class ProductsViewModel(
    private val productsRepository: ProductRepository = ProductDummyRepositoryImpl,
    private val cartRepository: CartRepository = CartDummyRepositoryImpl,
) : ViewModel() {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList<Product>())
    val products: LiveData<List<Product>> get() = _products

    private val _isLoadingProducts: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoadingProducts: LiveData<Boolean> get() = _isLoadingProducts

    private val _cart = MutableLiveData<Map<Int, Int>>(emptyMap())
    val cart: LiveData<Map<Int, Int>> = _cart

    init {
        _cart.value = cartRepository.fetchAllProduct().associate { it.product.id to it.count }
    }

    fun reloadCart() {
        _cart.value = cartRepository.fetchAllProduct().associate { it.product.id to it.count }
    }

    fun updateProducts(count: Int = SHOWN_PRODUCTS_COUNT) {
        val newProducts =
            productsRepository.fetchProducts(count, products.value?.lastOrNull()?.id ?: 0)
        _products.value = products.value?.plus(newProducts)
    }

    fun updateIsLoadable() {
        _isLoadingProducts.value =
            productsRepository.fetchIsProductsLoadable(products.value?.lastOrNull()?.id ?: 0)
    }

    fun update(
        id: Int,
        count: Int,
    ) {
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
        val nowProducts = _products.value?.toMutableList()
        val product = nowProducts?.find { it.id == id }
        if (product != null) {
            update(id, 1)
            cartRepository.upsertCartProduct(product, 1)
        }
    }

    fun downCount(id: Int) {
        val nowProducts = _products.value?.toMutableList()
        val product = nowProducts?.find { it.id == id }
        if (product != null) {
            update(id, -1)
            cartRepository.upsertCartProduct(product, -1)
        }
    }

    companion object {
        private const val SHOWN_PRODUCTS_COUNT: Int = 20
    }
}
