package woowacourse.shopping.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.data.DummyShoppingCart
import woowacourse.shopping.domain.Product
import kotlin.math.min

class ProductsViewModel : ViewModel() {
    private var allProducts: Set<Product> = DummyProducts.products.toSet()
    private val _productsLiveData: MutableLiveData<List<Product>> = MutableLiveData()
    private val _pageLiveData: MutableLiveData<Int> = MutableLiveData()

    val totalSize: Int get() = allProducts.size
    val productsLiveData: LiveData<List<Product>> get() = _productsLiveData
    val pageLiveData: LiveData<Int> get() = _pageLiveData

    val products: List<Product> get() = _productsLiveData.value ?: requestProductsPage(1)

    fun requestProductsPage(page: Int): List<Product> {
        val until = min(page*PAGE_SIZE, allProducts.size)
        _productsLiveData.value = allProducts.toList().subList(0, until)
        _pageLiveData.value = page
        return _productsLiveData.value ?: emptyList()
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
