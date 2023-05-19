package woowacourse.shopping.view.cart

import androidx.lifecycle.LiveData
import woowacourse.shopping.domain.CartPageStatus
import woowacourse.shopping.domain.CartSystemResult
import woowacourse.shopping.model.CartProductModel

interface CartContract {
    interface View {
        fun showProducts(items: List<CartViewItem>)
        fun showChangedItems()
        fun showChangedItem(position: Int)
    }

    interface Presenter {
        val cartSystemResult: LiveData<CartSystemResult>
        val cartPageStatus: LiveData<CartPageStatus>
        val isCheckedAll: LiveData<Boolean>

        fun fetchProducts()
        fun removeProduct(id: Int)
        fun fetchNextPage()
        fun fetchPrevPage()
        fun updateCartProductCount(id: Int, count: Int)
        fun selectProduct(product: CartProductModel)
        fun selectAll(isChecked: Boolean)
    }
}
