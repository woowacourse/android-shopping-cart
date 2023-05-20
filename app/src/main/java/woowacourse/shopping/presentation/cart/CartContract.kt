package woowacourse.shopping.presentation.cart

import woowacourse.shopping.CartProductInfo
import woowacourse.shopping.CartProductInfoList
import woowacourse.shopping.presentation.model.CartProductInfoModel
import woowacourse.shopping.util.OffsetPaging
import woowacourse.shopping.util.SafeLiveData

interface CartContract {
    interface Presenter {
        val paging: OffsetPaging<CartProductInfo>
        val loadedCartProducts: SafeLiveData<CartProductInfoList>
        val pageProducts: SafeLiveData<CartProductInfoList>
        fun loadCurrentPageProducts()
        fun plusPage()
        fun minusPage()
        fun checkPlusPageAble()
        fun checkMinusPageAble()
        fun deleteProductItem(position: Int)
        fun addProductInOrder(position: Int)
        fun deleteProductInOrder(position: Int)
        fun updateProductCount(position: Int, count: Int)
        fun updateCurrentPageCartView()
        fun changeCurrentPageProductsOrder()
    }

    interface View {
        fun setCartItems(productModels: List<CartProductInfoModel>)
        fun setUpPlusPageState(isEnable: Boolean)
        fun setUpMinusPageState(isEnable: Boolean)
    }
}
