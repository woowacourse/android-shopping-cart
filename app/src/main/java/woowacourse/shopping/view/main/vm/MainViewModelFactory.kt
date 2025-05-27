package woowacourse.shopping.view.main.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.view.ShoppingApp.Companion.cartRepository
import woowacourse.shopping.view.ShoppingApp.Companion.productRepository
import woowacourse.shopping.view.ShoppingApp.Companion.recentProductRepository

class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(cartRepository, productRepository, recentProductRepository) as T
        }
        throw IllegalArgumentException()
    }
}
