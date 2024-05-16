package woowacourse.shopping.presentation.ui.shopping

import android.view.View.GONE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingItemsRepository

class ShoppingViewModel(val repository: ShoppingItemsRepository) : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

    private val productsData: List<Product> by lazy { repository.getAllProducts() }

    private val _visibility = MutableLiveData<Int>()
    val visibility: LiveData<Int>
        get() = _visibility

    private var offset = 0

    init {
        loadProducts()
        updateVisibility(GONE)
    }

    private fun getProducts(): List<Product> {
        offset = Integer.min(offset + PAGE_SIZE, productsData.size)
        return productsData.subList(0, offset)
    }

    fun loadProducts() {
        _products.postValue(getProducts())
    }

    fun updateVisibility(visibility: Int) {
        _visibility.postValue(visibility)
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}
