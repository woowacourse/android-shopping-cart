package woowacourse.shopping.presentation.base.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.data.repsoitory.DummyProductList
import woowacourse.shopping.data.repsoitory.DummyShoppingCart
import woowacourse.shopping.presentation.ui.productdetail.ProductDetailViewModel
import woowacourse.shopping.presentation.ui.productlist.ProductListViewModel
import woowacourse.shopping.presentation.ui.shoppingcart.ShoppingCartViewModel

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T {
        return when {
            (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) -> {
                ProductDetailViewModel(
                    extras.createSavedStateHandle(),
                    DummyProductList,
                    DummyShoppingCart,
                ) as T
            }

            (modelClass.isAssignableFrom(ProductListViewModel::class.java)) -> {
                ShoppingCartViewModel(DummyShoppingCart) as T
            }

            (modelClass.isAssignableFrom(ShoppingCartViewModel::class.java)) -> {
                ShoppingCartViewModel(DummyShoppingCart) as T
            }

            else -> throw IllegalArgumentException()
        }
    }
}
