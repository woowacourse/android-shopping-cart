package woowacourse.shopping.view.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.DummyShoppingCart
import woowacourse.shopping.domain.Product
import kotlin.math.min

class ShoppingCartViewModel : ViewModel() {
    private var allProducts: Set<Product> = DummyShoppingCart.products.toSet()
    private val _productsLiveData: MutableLiveData<List<Product>> = MutableLiveData()
    private val _paginationLeftLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val _paginationRightLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val _pageLiveData: MutableLiveData<Int> = MutableLiveData()

    val productsLiveData: LiveData<List<Product>> get() = _productsLiveData
    val paginationLeftLiveData: LiveData<Boolean> get() = _paginationLeftLiveData
    val paginationRightLiveData: LiveData<Boolean> get() = _paginationRightLiveData
    val pageLiveData: LiveData<Int> get() = _pageLiveData

    val products: List<Product> get() = _productsLiveData.value ?: requestProductsPage(0)

    fun addProduct(product: Product) {
        allProducts = allProducts + product
    }

    fun removeProduct(product: Product) {
        val currentProductIndex = allProducts.indexOf(product)
        allProducts = allProducts - product
        requestProductsPage(currentProductIndex / PAGE_SIZE)
    }

    fun requestProductsPage(page: Int): List<Product> {
        val from = page * PAGE_SIZE
        val until = min(from + PAGE_SIZE, allProducts.size)
        _productsLiveData.value = allProducts.toList().subList(from, until)
        _paginationLeftLiveData.value = page > 0
        _paginationRightLiveData.value = until < allProducts.size
        _pageLiveData.value = page
        return _productsLiveData.value ?: emptyList()
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}
