package woowacourse.shopping.view.cart

interface CartContract {
    interface View {
        fun showProducts(items: List<CartViewItem>)
        fun showChangedItems()

        fun showChangedItem(position: Int)
    }

    interface Presenter {
        fun fetchProducts()
        fun removeProduct(id: Int)
        fun fetchNextPage()
        fun fetchPrevPage()
        fun updateCartProductCount(id: Int, count: Int)
    }
}
