package woowacourse.shopping.view.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.data.product.ProductUrls.url
import woowacourse.shopping.domain.product.Product

class ProductDetailViewModel : ViewModel() {
    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> get() = _product

    val imageUrl: LiveData<String> = _product.map { it.url ?: "" }

    fun updateProduct(product: Product) {
        _product.value = product
    }
}
