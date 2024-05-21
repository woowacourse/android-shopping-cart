package woowacourse.shopping.presentation.ui.shoppingcart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import java.lang.IllegalArgumentException

class ShoppingCartViewModelFactory(
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ShoppingCartViewModel::class.java)) {
            ShoppingCartViewModel(
                shoppingCartRepository,
            ) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
