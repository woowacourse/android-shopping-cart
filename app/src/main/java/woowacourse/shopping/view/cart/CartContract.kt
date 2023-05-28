package woowacourse.shopping.view.cart

import androidx.lifecycle.LiveData
import woowacourse.shopping.domain.cartsystem.CartPageStatus
import woowacourse.shopping.domain.cartsystem.CartSystemResult
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
        fun fetchNextPage()
        fun fetchPrevPage()
        fun removeProduct(id: Int)
        fun updateCartProductCount(id: Int, count: Int)
        fun checkProduct(product: CartProductModel)
        fun checkProductsAll()
    }
}
