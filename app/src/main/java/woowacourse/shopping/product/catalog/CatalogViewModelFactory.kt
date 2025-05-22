package woowacourse.shopping.product.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.repository.CartProductRepositoryImpl

class CatalogViewModelFactory(
    private val application: ShoppingApplication,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatalogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CatalogViewModel(
                cartProductRepository =
                    CartProductRepositoryImpl(
                        ShoppingDatabase.getInstance(application).cartProductDao(),
                    ),
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
