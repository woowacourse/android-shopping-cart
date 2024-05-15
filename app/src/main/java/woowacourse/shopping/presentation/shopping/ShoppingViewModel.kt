package woowacourse.shopping.presentation.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingItemsRepository

class ShoppingViewModel(val repository: ShoppingItemsRepository) : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product>
        get() = _product

    private val productsData: List<Product> by lazy { repository.getAllProducts() }

    private var offset = 0

    init {
        loadProducts()
    }

    private fun getProducts(): List<Product> {
        offset = Integer.min(offset + PAGE_SIZE, productsData.size)
        return productsData.subList(0, offset)
    }

    fun loadProducts() {
        _products.postValue(getProducts())
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}
