package woowacourse.shopping.view.productdetail

import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.db.CartProductDBRepository
import woowacourse.shopping.data.db.RecentProductDBRepository
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    override val product: ProductUIModel
) : ProductDetailContract.Presenter {

    override fun saveRecentProduct(db: SQLiteDatabase) {
        val repository = RecentProductDBRepository(db)
        repository.insert(RecentProductUIModel(product))
        db.close()
    }

    override fun saveCartProduct(db: SQLiteDatabase) {
        val repository = CartProductDBRepository(db)
        repository.insert(CartProductUIModel(product))
        db.close()
        view.showCartPage()
    }
}
