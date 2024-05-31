package woowacourse.shopping.presentation.ui.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.RecentlyViewedProductsRepository
import woowacourse.shopping.domain.repository.ShoppingItemsRepository

class ShoppingViewModelFactory(
    private val shoppingItemsRepository: ShoppingItemsRepository,
    private val cartRepository: CartRepository,
    private val recentlyViewedProductsRepository: RecentlyViewedProductsRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShoppingViewModel(
                shoppingRepository = shoppingItemsRepository,
                cartRepository = cartRepository,
                recentlyViewedProductsRepository = recentlyViewedProductsRepository,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
