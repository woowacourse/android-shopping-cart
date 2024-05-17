package woowacourse.shopping.presentation.ui.shopping

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

    private val _isBtnVisibleCondition = MutableLiveData<Boolean>()
    val isBtnVisibleCondition: LiveData<Boolean> = _isBtnVisibleCondition

    private var offset = 0

    init {
        loadProducts()
        hideLoadMoreBtn()
    }

    fun loadNextProducts() {
        loadProducts()
        hideLoadMoreBtn()
    }

    private fun getProducts(): List<Product> {
        offset = Integer.min(offset + PAGE_SIZE, productsData.size)
        return productsData.subList(0, offset)
    }

    private fun loadProducts() {
        _products.postValue(getProducts())
    }

    fun showLoadMoreBtn() {
        _isBtnVisibleCondition.postValue(true)
    }

    fun hideLoadMoreBtn() {
        _isBtnVisibleCondition.postValue(false)
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}
