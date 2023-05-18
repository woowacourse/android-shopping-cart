package woowacourse.shopping.presentation.productlist.product

import woowacourse.shopping.presentation.model.ProductModel

interface ProductItemContract {
    interface Presenter {
        fun loadProductCount(product: ProductModel)
        fun changeProductCount(
            product: ProductModel,
            count: Int,
        )

        fun putProductInCart(product: ProductModel)
    }

    interface View {
        fun setAddCartEnable(isEnabled: Boolean)
        fun setProductCount(count: Int)
    }
}
