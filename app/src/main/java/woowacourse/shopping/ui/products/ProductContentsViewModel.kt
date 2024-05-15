package woowacourse.shopping.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductsImpl
import kotlin.math.min

class ProductContentsViewModel : ViewModel() {
    private var currentOffset = 0
    private val items: MutableList<Product> = mutableListOf()

    private val _products: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>> get() = _products

    fun loadProducts() {
        items.clear()
        items.addAll(ProductsImpl.findAll())
        _products.value = getProducts()
    }

    private fun getProducts(): List<Product> {
        val endRange = min(currentOffset + LOAD_LIMIT, items.size)
        val productsInRange = items.toList().subList(currentOffset, endRange)
        currentOffset = endRange

        return productsInRange
    }

    companion object {
        private const val LOAD_LIMIT = 20
    }
}
