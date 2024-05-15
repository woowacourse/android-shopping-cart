package woowacourse.shopping.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.remote.DummyCartRepository
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.presentation.ui.detail.ProductDetailViewModel
import woowacourse.shopping.presentation.ui.shopping.ShoppingViewModel

class ViewModelFactory(private val repository: ProductRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProductDetailViewModel::class.java) -> {
                ProductDetailViewModel(repository, DummyCartRepository) as T
            }

            modelClass.isAssignableFrom(ShoppingViewModel::class.java) -> {
                ShoppingViewModel(repository) as T
            }

            else -> {
                throw IllegalArgumentException()
            }
        }
    }
}
