package woowacourse.shopping.productlist

interface ProductListContract {
    interface ViewAction {
        fun onProductClicked(id: Long)
    }
}
