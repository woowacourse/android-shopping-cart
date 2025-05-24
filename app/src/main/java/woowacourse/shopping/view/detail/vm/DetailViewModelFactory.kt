package woowacourse.shopping.view.detail.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.view.ShoppingApp.Companion.cartRepository

class DetailViewModelFactory :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(cartRepository) as T
        }
        throw IllegalArgumentException()
    }
}
