package woowacourse.shopping.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ShoppingRepository

class HomeViewModelFactory(
    private val shoppingRepository: ShoppingRepository,
    private val cartRepository: CartRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(
                shoppingRepository = shoppingRepository,
                cartRepository = cartRepository,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
