package woowacourse.shopping.ui.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.product.Product

class DetailViewModelFactory(private val product: Product) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(product) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
