package woowacourse.shopping.view.product.catalog

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.ShoppingCartDatabase
import woowacourse.shopping.data.cart.LocalCartProductRepository
import woowacourse.shopping.data.product.LocalProductRepository

class ProductCatalogViewModelFactory(
    private val applicationContext: Context,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductCatalogViewModel::class.java)) {
            val productRepository = LocalProductRepository()
            val database = ShoppingCartDatabase.getDataBase(applicationContext)
            val cartProductRepository = LocalCartProductRepository(database.cartProductDao)
            return ProductCatalogViewModel(productRepository, cartProductRepository) as T
        }
        throw IllegalArgumentException()
    }
}
