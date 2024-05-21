package woowacourse.shopping.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.DummyProductRepository
import woowacourse.shopping.DummyShoppingRepository
import woowacourse.shopping.productdetail.ProductDetailViewModel
import woowacourse.shopping.productlist.ProductListViewModel
import woowacourse.shopping.shoppingcart.ShoppingCartViewModel

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProductDetailViewModel::class.java) -> {
                ProductDetailViewModel(DummyProductRepository, DummyShoppingRepository) as T
            }

            modelClass.isAssignableFrom(ProductListViewModel::class.java) -> {
                val repository = DummyProductRepository
                ProductListViewModel(repository) as T
            }

            modelClass.isAssignableFrom(ShoppingCartViewModel::class.java) -> {
                val repository = DummyShoppingRepository
                ShoppingCartViewModel(repository) as T
            }

            else -> error("Failed to create ViewModel : ${modelClass.name}")
        }
    }
}
