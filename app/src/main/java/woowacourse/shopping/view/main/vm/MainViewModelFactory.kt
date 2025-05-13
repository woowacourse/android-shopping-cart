package woowacourse.shopping.view.main.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.ProductStorage
import woowacourse.shopping.data.ProductStorageImpl

class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val storage: ProductStorage = ProductStorageImpl()

        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(storage) as T
        }
        throw IllegalArgumentException()
    }
}
