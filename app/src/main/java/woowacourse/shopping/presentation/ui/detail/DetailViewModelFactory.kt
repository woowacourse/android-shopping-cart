package woowacourse.shopping.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.RecentlyViewedProductsRepository
import woowacourse.shopping.domain.repository.ShoppingItemsRepository

class DetailViewModelFactory(
    private val cartRepository: CartRepository,
    private val shoppingRepository: ShoppingItemsRepository,
    private val recentlyViewedProductsRepository: RecentlyViewedProductsRepository,
    private val productId: Long,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(
                cartRepository = cartRepository,
                shoppingRepository = shoppingRepository,
                recentlyViewedProductsRepository = recentlyViewedProductsRepository,
                productId = productId,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
