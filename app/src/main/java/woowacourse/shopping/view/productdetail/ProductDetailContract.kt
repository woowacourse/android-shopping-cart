package woowacourse.shopping.view.productdetail

import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.uimodel.ProductUIModel

interface ProductDetailContract {
    interface View {
        var presenter: Presenter
        fun setProductDetailView()
        fun showCartPage()
    }

    interface Presenter {
        val product: ProductUIModel
        fun saveRecentProduct(db: SQLiteDatabase)
        fun saveCartProduct(db: SQLiteDatabase)
    }
}
