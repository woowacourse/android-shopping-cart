package woowacourse.shopping.presentation.cart

import woowacourse.shopping.presentation.model.ProductModel

interface CartContract {
    interface Presenter {
        fun initCart()
        fun deleteProduct(productModel: ProductModel)
    }

    interface View {
        fun initCartProductModels(productModels: List<ProductModel>)
        fun setCartProductModels(productModels: List<ProductModel>)
    }
}
