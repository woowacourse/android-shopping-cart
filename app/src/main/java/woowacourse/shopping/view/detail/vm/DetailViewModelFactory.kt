package woowacourse.shopping.view.detail.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.ProductStorage
import woowacourse.shopping.data.ProductStorageImpl

class DetailViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            val storage: ProductStorage = ProductStorageImpl()
            return DetailViewModel(storage) as T
        }
        throw IllegalArgumentException()
    }
}
