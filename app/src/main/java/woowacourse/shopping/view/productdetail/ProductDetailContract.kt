package woowacourse.shopping.view.productdetail

import androidx.lifecycle.LiveData
import woowacourse.shopping.model.ProductModel

interface ProductDetailContract {
    interface View {

        fun forceQuit()
        fun setUpDialogBinding(product: ProductModel)
        fun setUpProductDetailView(product: ProductModel)
        fun setUpLastViewedProductView(lastViewedProduct: ProductModel)
        fun finishActivity(isAdd: Boolean)
    }

    interface Presenter {
        val count: LiveData<Int>
        fun fetchProduct()
        fun putInCart(product: ProductModel)
        fun plusCount()
        fun minusCount()
    }
}
