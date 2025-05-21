package woowacourse.shopping.view.detail.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.storage.CartStorage
import woowacourse.shopping.data.storage.CartStorageImpl
import woowacourse.shopping.data.storage.ProductStorage
import woowacourse.shopping.data.storage.ProductStorageImpl

class DetailViewModelFactory(private val shoppingDatabase: ShoppingDatabase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            val productStorage: ProductStorage = ProductStorageImpl()
            val cartStorage: CartStorage = CartStorageImpl(shoppingDatabase.productDao())

            return DetailViewModel(productStorage, cartStorage) as T
        }
        throw IllegalArgumentException()
    }
}
