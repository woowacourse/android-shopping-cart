package woowacourse.shopping.view.productcatalogue

import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

interface ProductCatalogueContract {
    interface View {
        var presenter: Presenter

        fun showProductDetailPage(): (ProductUIModel) -> Unit
    }

    interface Presenter {
        fun getMainProducts(): List<ProductUIModel>
        fun getRecentProducts(db: SQLiteDatabase): List<RecentProductUIModel>
        fun setProductOnClick()
    }
}
