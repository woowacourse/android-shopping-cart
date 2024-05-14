package woowacourse.shopping.productList

import androidx.lifecycle.ViewModel
import woowacourse.shopping.db.Product
import woowacourse.shopping.repository.DummyProductStore

class ProductListViewModel : ViewModel() {
    private val dummyProductStore by lazy { DummyProductStore() }
    private var currentIndex = 0
    fun loadProducts(): List<Product> {
        val result = dummyProductStore.load20Data(currentIndex)
        currentIndex += 20
        return result
    }
}
