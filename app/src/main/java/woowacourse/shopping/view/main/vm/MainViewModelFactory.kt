package woowacourse.shopping.view.main.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.storage.ProductStorage

class MainViewModelFactory : ViewModelProvider.Factory {
    private val productStorage = ProductStorage()
    private val productRepository = ProductRepositoryImpl(productStorage)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(productRepository) as T
        }
        throw IllegalArgumentException()
    }
}
