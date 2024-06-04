package woowacourse.shopping.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.db.AppDatabase
import woowacourse.shopping.data.db.cart.RoomCartRepository
import woowacourse.shopping.data.db.recent.LocalRecentProductRepository
import woowacourse.shopping.data.remote.RemoteProductRepositoryImpl
import woowacourse.shopping.presentation.ui.cart.CartViewModel
import woowacourse.shopping.presentation.ui.detail.ProductDetailViewModel
import woowacourse.shopping.presentation.ui.shopping.ShoppingViewModel

class ViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProductDetailViewModel::class.java) -> {
                val recentDao = AppDatabase.instanceOrNull.recentProductDao()
                val cartDao = AppDatabase.instanceOrNull.cartDao()
                ProductDetailViewModel(
                    RemoteProductRepositoryImpl(),
                    RoomCartRepository(cartDao),
                    LocalRecentProductRepository(recentDao),
                ) as T
            }

            modelClass.isAssignableFrom(ShoppingViewModel::class.java) -> {
                val recentDao = AppDatabase.instanceOrNull.recentProductDao()
                val cartDao = AppDatabase.instanceOrNull.cartDao()
                ShoppingViewModel(
                    productRepository = RemoteProductRepositoryImpl(),
                    recentRepository = LocalRecentProductRepository(recentDao),
                    cartRepository = RoomCartRepository(cartDao),
                ) as T
            }

            modelClass.isAssignableFrom(CartViewModel::class.java) -> {
                val cartDao = AppDatabase.instanceOrNull.cartDao()
                CartViewModel(cartRepository = RoomCartRepository(cartDao)) as T
            }

            else -> {
                throw IllegalArgumentException()
            }
        }
    }
}
