package woowacourse.shopping.view.productdetail

import androidx.lifecycle.LiveData
import woowacourse.shopping.model.ProductModel

interface ProductDetailContract {
    interface View {
        fun finishActivity(isAdd: Boolean)
    }

    interface Presenter {
        val count: LiveData<Int>
        fun putInCart(product: ProductModel)
        fun updateRecentViewedProducts(id: Int)
        fun plusCount()
        fun minusCount()
    }
}
