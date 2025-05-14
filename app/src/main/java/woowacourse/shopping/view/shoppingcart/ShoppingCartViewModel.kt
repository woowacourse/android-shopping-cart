package woowacourse.shopping.view.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.DummyShoppingCart
import woowacourse.shopping.domain.Product
import kotlin.math.min

class ShoppingCartViewModel : ViewModel() {
    private val _allProductsLiveData: MutableLiveData<List<Product>> =
        MutableLiveData(
            DummyShoppingCart.products,
        )
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
        _productsLiveData.value = products + product
    }

    fun removeProduct(product: Product) {
        _productsLiveData.value = products - product
    }

    fun requestProductsPage(page: Int): List<Product> {
        val from = page * PAGE_SIZE
        val until = min(from + PAGE_SIZE, _allProductsLiveData.value!!.size)
        _productsLiveData.value = _allProductsLiveData.value!!.subList(from, until)
        _paginationLeftLiveData.value = page > 0
        _paginationRightLiveData.value = until < _allProductsLiveData.value!!.size
        _pageLiveData.value = page
        return _productsLiveData.value ?: emptyList()
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}
