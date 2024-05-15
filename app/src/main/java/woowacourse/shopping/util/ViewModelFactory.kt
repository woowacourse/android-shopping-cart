package woowacourse.shopping.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.DummyShoppingRepository
import woowacourse.shopping.productdetail.ProductDetailViewModel

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProductDetailViewModel::class.java) -> {
                val repository = DummyShoppingRepository
                ProductDetailViewModel(repository) as T
            }
            else -> error("Failed to create ViewModel : ${modelClass.name}")
        }
    }
}
