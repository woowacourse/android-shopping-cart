package woowacourse.shopping.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.DummyProductRepository
import woowacourse.shopping.database.ProductDataBase
import woowacourse.shopping.database.recentviewedproducts.RecentlyViewedProductsRepository
import woowacourse.shopping.database.shoppingcart.ShoppingCartRepository
import woowacourse.shopping.productdetail.ProductDetailViewModel
import woowacourse.shopping.productlist.ProductListViewModel
import woowacourse.shopping.productlist.RecentlyViewedProductsViewModel
import woowacourse.shopping.shoppingcart.ShoppingCartViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    private val database = ProductDataBase.getDatabase(context)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProductDetailViewModel::class.java) -> {
                val shoppingCartRepository = ShoppingCartRepository(database.shoppingCartDao())
                val productRepository = DummyProductRepository
                val recentlyViewedProductsRepository =
                    RecentlyViewedProductsRepository(database.recentlyViewedProductDao())
                ProductDetailViewModel(
                    productRepository,
                    shoppingCartRepository,
                    recentlyViewedProductsRepository,
                ) as T
            }

            modelClass.isAssignableFrom(ProductListViewModel::class.java) -> {
                val shoppingCartRepository = ShoppingCartRepository(database.shoppingCartDao())
                val productRepository = DummyProductRepository
                ProductListViewModel(productRepository, shoppingCartRepository) as T
            }

            modelClass.isAssignableFrom(RecentlyViewedProductsViewModel::class.java) -> {
                val recentlyViewedProductsRepository =
                    RecentlyViewedProductsRepository(database.recentlyViewedProductDao())
                val productRepository = DummyProductRepository
                RecentlyViewedProductsViewModel(
                    productRepository,
                    recentlyViewedProductsRepository,
                ) as T
            }

            modelClass.isAssignableFrom(ShoppingCartViewModel::class.java) -> {
                val shoppingCartRepository = ShoppingCartRepository(database.shoppingCartDao())
                ShoppingCartViewModel(shoppingCartRepository) as T
            }

            else -> error("Failed to create ViewModel : ${modelClass.name}")
        }
    }
}
