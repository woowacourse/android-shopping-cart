package woowacourse.shopping.ui.productList

import androidx.lifecycle.ViewModel
import woowacourse.shopping.ui.util.SingleLiveData
import woowacourse.shopping.ui.productList.event.ProductListError
import woowacourse.shopping.ui.productList.event.ProductListEvent

abstract class ProductListViewModel : ViewModel(), ProductListListener{
    abstract val uiState: ProductListUiState

    abstract val errorEvent: SingleLiveData<ProductListError>

    abstract val navigationEvent: SingleLiveData<ProductListEvent>

    abstract fun loadAll()

    abstract fun loadNextPageProducts()

    abstract fun navigateToShoppingCart()
}