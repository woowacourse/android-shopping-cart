package woowacourse.shopping.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.local.AppDatabase
import woowacourse.shopping.data.local.LocalRecentProductRepository
import woowacourse.shopping.data.remote.DummyCartRepository
import woowacourse.shopping.data.remote.DummyProductRepository
import woowacourse.shopping.presentation.ui.cart.CartViewModel
import woowacourse.shopping.presentation.ui.detail.ProductDetailViewModel
import woowacourse.shopping.presentation.ui.shopping.ShoppingViewModel

class ViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProductDetailViewModel::class.java) -> {
                val recentDao = AppDatabase.instanceOrNull.recentProductDao()
                ProductDetailViewModel(
                    DummyProductRepository(),
                    DummyCartRepository,
                    LocalRecentProductRepository(recentDao),
                ) as T
            }

            modelClass.isAssignableFrom(ShoppingViewModel::class.java) -> {
                val dao = AppDatabase.instanceOrNull.recentProductDao()
                ShoppingViewModel(recentRepository = LocalRecentProductRepository(dao)) as T
            }

            modelClass.isAssignableFrom(CartViewModel::class.java) -> {
                CartViewModel(DummyCartRepository) as T
            }

            else -> {
                throw IllegalArgumentException()
            }
        }
    }
}
