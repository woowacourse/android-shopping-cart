package woowacourse.shopping.productList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.db.Product
import woowacourse.shopping.repository.DummyProductStore

class ProductListViewModel : ViewModel() {
    val productImage = MutableLiveData<String>()
    val productName = MutableLiveData<String>()
    val productPrice = MutableLiveData<Int>()
    private val dummyProductStore by lazy { DummyProductStore() }
    private var currentIndex = 0
    fun loadProducts(): List<Product> {
        val result = dummyProductStore.load20Data(currentIndex)
        currentIndex += 20
        return result
    }
}
