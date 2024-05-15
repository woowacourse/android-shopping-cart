package woowacourse.shopping.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingItemsRepository

class DetailViewModel(val repository: ShoppingItemsRepository, val productId: Long) : ViewModel() {
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product>
        get() = _product

    init {
        loadProductData()
    }

    private fun loadProductData() {
        _product.postValue(repository.findProductItem(productId))
    }
}
