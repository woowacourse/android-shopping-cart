package woowacourse.shopping.feature.product.detail

import woowacourse.shopping.databinding.DialogSelectCountBinding
import woowacourse.shopping.model.ProductState

interface ProductDetailContract {

    interface View {
        fun setViewContent(product: ProductState)
        fun setCount(selectCountDialogBinding: DialogSelectCountBinding, count: Int)
        fun showCart()
        fun showAccessError()
        fun showSelectCountDialog()
        fun closeProductDetail()
    }

    interface Presenter {
        val product: ProductState?

        fun loadProduct()
        fun selectCount()
        fun addCartProduct(count: Int)
        fun plusCount(selectCountDialogBinding: DialogSelectCountBinding)
        fun minusCount(selectCountDialogBinding: DialogSelectCountBinding)
    }
}
