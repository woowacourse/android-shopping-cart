package woowacourse.shopping.view.cart

import woowacourse.shopping.model.CartProductModel

interface CartContract {
    interface View {
        fun showProducts(items: List<CartViewItem>)
        fun showChangedItems()
        fun showChangedItem(position: Int)
        fun showTotalResult(selectAll: Boolean, totalPrice: Int, totalCount: Int)
    }

    interface Presenter {
        fun fetchProducts()
        fun removeProduct(id: Int)
        fun fetchNextPage()
        fun fetchPrevPage()
        fun updateCartProductCount(id: Int, count: Int)
        fun selectProduct(product: CartProductModel)
        fun selectAll(isChecked: Boolean)
    }
}
