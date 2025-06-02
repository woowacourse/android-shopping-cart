package woowacourse.shopping.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.local.repository.CartRepository
import woowacourse.shopping.data.local.repository.HistoryRepository
import woowacourse.shopping.data.local.repository.ProductRepository
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.ui.cart.CartViewModel
import woowacourse.shopping.ui.fashionlist.ProductListViewModel
import woowacourse.shopping.ui.productdetail.DetailViewModel

object ViewModelFactory {
    fun createCartViewModelFactory(
        productRepository: ProductRepository,
        cartRepository: CartRepository,
        historyRepository: HistoryRepository
    ): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProductListViewModel(
                    productRepository,
                    cartRepository,
                    historyRepository
                ) as T
            }
        }
    }

    fun createCartViewModelFactory(
        cartRepository: CartRepository,
    ): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CartViewModel(cartRepository) as T
            }
        }
    }

    fun createDetailViewModelFactory(
        productRepository: ProductRepository,
        cartRepository: CartRepository,
        historyRepository: HistoryRepository,
        product: Product,
    ): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DetailViewModel(
                    productRepository,
                    cartRepository,
                    historyRepository,
                    product
                ) as T
            }
        }
    }
}
