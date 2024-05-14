package woowacourse.shopping.productList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.db.Product

class ProductListViewModel : ViewModel() {
    val productImage = MutableLiveData<String>()
    val productName = MutableLiveData<String>()
    val productPrice = MutableLiveData<Int>()
}
