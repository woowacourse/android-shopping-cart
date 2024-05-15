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

    init {
        loadProducts()
    }

    private fun loadProducts() {
        val productData = repository.getAllProducts()
        _products.postValue(productData)
    }

    fun loadProductData(productId: Long) {
        _product.postValue(repository.findProductItem(productId))
    }
}
