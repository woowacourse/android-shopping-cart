package woowacourse.shopping.shopping.contract

import woowacourse.shopping.model.ProductUIModel

interface ShoppingContract {
    interface View {
        var presenter: Presenter
        fun initProductsRecyclerView(data: List<ProductUIModel>)
        fun onItemClick(data: ProductUIModel)
    }

    interface Presenter {
        fun initProductsRecyclerView()
        fun onItemClick(data: ProductUIModel)
    }
}
