package woowacourse.shopping.view.detail.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.view.ShoppingApp.Companion.cartRepository
import woowacourse.shopping.view.ShoppingApp.Companion.productRepository
import woowacourse.shopping.view.ShoppingApp.Companion.recentProductRepository

class DetailViewModelFactory :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(cartRepository, productRepository, recentProductRepository) as T
        }
        throw IllegalArgumentException()
    }
}
