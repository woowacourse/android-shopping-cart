package woowacourse.shopping.product.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.repository.CartProductRepositoryImpl
import woowacourse.shopping.data.repository.HttpCatalogProductRepositoryImpl
import woowacourse.shopping.data.repository.RecentlyViewedProductRepositoryImpl
import woowacourse.shopping.data.server.DevMockServer

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
                recentlyViewedProductRepository =
                    RecentlyViewedProductRepositoryImpl(
                        ShoppingDatabase.getInstance(application).recentlyViewedProductDao(),
                        HttpCatalogProductRepositoryImpl(DevMockServer.baseUrl),
                    ),
                catalogProductRepository = HttpCatalogProductRepositoryImpl(DevMockServer.baseUrl),
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
