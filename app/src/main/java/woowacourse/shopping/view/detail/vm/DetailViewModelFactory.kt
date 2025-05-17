package woowacourse.shopping.view.detail.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.storage.CartStorage
import woowacourse.shopping.data.storage.ProductStorage
import woowacourse.shopping.domain.repository.CartRepository

class DetailViewModelFactory : ViewModelProvider.Factory {
    private val productStorage = ProductStorage()
    private val productRepository = ProductRepositoryImpl(productStorage)
    private val cartStorage: CartStorage = CartStorage

    private val cartRepository: CartRepository = CartRepositoryImpl(cartStorage)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(productRepository, cartRepository) as T
        }
        throw IllegalArgumentException()
    }
}
