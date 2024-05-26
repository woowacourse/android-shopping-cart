package woowacourse.shopping.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.productdetail.ProductDetailViewModel
import woowacourse.shopping.productlist.ProductListViewModel
import woowacourse.shopping.repository.DefaultProductRepository
import woowacourse.shopping.repository.DefaultShoppingRepository
import woowacourse.shopping.shoppingcart.ShoppingCartViewModel

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProductDetailViewModel::class.java) -> {
                ProductDetailViewModel(
                    DefaultProductRepository.instance(),
                    DefaultShoppingRepository.instance(),
                ) as T
            }

            modelClass.isAssignableFrom(ProductListViewModel::class.java) -> {
                ProductListViewModel(
                    DefaultProductRepository.instance(),
                    DefaultShoppingRepository.instance(),
                ) as T
            }

            modelClass.isAssignableFrom(ShoppingCartViewModel::class.java) -> {
                val repository = DefaultShoppingRepository.instance()
                ShoppingCartViewModel(repository) as T
            }

            else -> error("Failed to create ViewModel : ${modelClass.name}")
        }
    }
}
