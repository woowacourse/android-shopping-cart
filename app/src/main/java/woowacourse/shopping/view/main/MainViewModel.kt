package woowacourse.shopping.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.ProductStorage
import woowacourse.shopping.domain.Product

class MainViewModel(
    private val storage: ProductStorage,
) : ViewModel() {
    private val _products = MutableLiveData(emptyList<Product>())
    val products: LiveData<List<Product>> = _products

    init {
        _products.value = storage.getAll()
    }
}
