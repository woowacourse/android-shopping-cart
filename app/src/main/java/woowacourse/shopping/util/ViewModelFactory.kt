package woowacourse.shopping.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.DummyProductRepository
import woowacourse.shopping.UserShoppingCartRepository
import woowacourse.shopping.productdetail.ProductDetailViewModel
import woowacourse.shopping.productlist.ProductListViewModel
import woowacourse.shopping.shoppingcart.ShoppingCartViewModel

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProductDetailViewModel::class.java) -> {
                val productRepository = DummyProductRepository
                val shoppingCartRepository = UserShoppingCartRepository
                ProductDetailViewModel(productRepository, shoppingCartRepository) as T
            }

            modelClass.isAssignableFrom(ProductListViewModel::class.java) -> {
                val productRepository = DummyProductRepository
                val shoppingCartRepository = UserShoppingCartRepository
                ProductListViewModel(productRepository, shoppingCartRepository) as T
            }

            modelClass.isAssignableFrom(ShoppingCartViewModel::class.java) -> {
                val repository = UserShoppingCartRepository
                ShoppingCartViewModel(repository) as T
            }

            else -> error("Failed to create ViewModel : ${modelClass.name}")
        }
    }
}
