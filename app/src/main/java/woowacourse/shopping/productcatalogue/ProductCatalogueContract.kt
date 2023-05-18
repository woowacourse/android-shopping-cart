package woowacourse.shopping.productcatalogue

import woowacourse.shopping.uimodel.RecentProductUIModel

interface ProductCatalogueContract {
    interface View {
        fun notifyDataChanged()
        fun setRecentProductList(recentProducts: List<RecentProductUIModel>)
        fun updateProductList(recentProducts: List<RecentProductUIModel>)
    }

    interface Presenter {
        fun getRecentProduct()
        fun readMoreOnClick(unitSize: Int, page: Int)
    }
}
