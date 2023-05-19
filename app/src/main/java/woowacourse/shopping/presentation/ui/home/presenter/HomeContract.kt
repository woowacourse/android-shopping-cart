package woowacourse.shopping.presentation.ui.home.presenter

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.ui.common.BaseView
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter.ProductsByView
import woowacourse.shopping.presentation.ui.home.uiModel.Operator
import woowacourse.shopping.presentation.ui.home.uiModel.ProductInCartUiState

interface HomeContract {
    interface View : BaseView<Presenter> {
        fun setUpProductsOnHome(
            products: List<ProductsByView>,
            shoppingCart: List<ProductInCartUiState>,
        )

        fun setUpMoreProducts(products: List<ProductsByView>)
        fun setUpCountOfProductInCart(productInCart: List<ProductInCartUiState>)
    }

    interface Presenter {
        fun fetchAllProductsOnHome()
        fun fetchMoreProducts()
        fun addCountOfProductInCart(request: Operator, productInCart: Product)
    }
}
