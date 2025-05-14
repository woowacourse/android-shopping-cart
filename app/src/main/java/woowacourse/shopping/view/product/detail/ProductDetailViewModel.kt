package woowacourse.shopping.view.product.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.Product

class ProductDetailViewModel(
    val product: Product,
) : ViewModel() {
    companion object {
        fun provideFactory(product: Product): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
                        return ProductDetailViewModel(product) as T
                    }
                    throw IllegalArgumentException()
                }
            }
    }
}
