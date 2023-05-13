package woowacourse.shopping.view.shoppingmain

import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.ProductMockData
import woowacourse.shopping.data.db.RecentProductDBRepository
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

class ShoppingMainPresenter(private val view: ShoppingMainContract.View) :
    ShoppingMainContract.Presenter {
    override fun getMainProducts(): List<ProductUIModel> {
        return ProductMockData.mainProductMockData
    }

    override fun getRecentProducts(db: SQLiteDatabase): List<RecentProductUIModel> {
        val repository = RecentProductDBRepository(db)
        val recentProducts = repository.getAll()
        repository.close()
        return recentProducts
    }

    override fun setProductOnClick() {
        view.showProductDetailPage()
    }
}
