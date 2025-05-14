package woowacourse.shopping.view.detail.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.CartStorage
import woowacourse.shopping.data.CartStorageImpl
import woowacourse.shopping.data.ProductStorage
import woowacourse.shopping.data.ProductStorageImpl

class DetailViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            val productStorage: ProductStorage = ProductStorageImpl()
            val cartStorage: CartStorage = CartStorageImpl

            return DetailViewModel(productStorage, cartStorage) as T
        }
        throw IllegalArgumentException()
    }
}
