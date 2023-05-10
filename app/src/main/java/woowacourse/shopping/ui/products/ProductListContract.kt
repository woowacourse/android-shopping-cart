package woowacourse.shopping.ui.products

interface ProductListContract {
    interface Presenter {
        fun loadProducts()
    }

    interface View {
        fun setProducts(products: List<ProductUIState>)
    }
}
