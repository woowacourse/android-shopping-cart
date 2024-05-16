package woowacourse.shopping.presentation.ui.shoppingcart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.domain.repository.ShoppingCartRepository

class ShoppingCartViewModelFactory(
    private val repository: ShoppingCartRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T {
        return if (modelClass.isAssignableFrom(ShoppingCartViewModel::class.java)) {
            ShoppingCartViewModel(repository) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
