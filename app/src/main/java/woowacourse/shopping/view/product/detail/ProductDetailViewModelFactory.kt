package woowacourse.shopping.view.product.detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepositoryImpl
import woowacourse.shopping.domain.Product

class ProductDetailViewModelFactory(
    private val product: Product,
    private val applicationContext: Context,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            val repository = ShoppingCartRepositoryImpl(applicationContext)
            return ProductDetailViewModel(product, repository) as T
        }
        throw IllegalArgumentException()
    }
}
