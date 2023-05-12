package woowacourse.shopping.view.productcatalogue

import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.ProductMockData
import woowacourse.shopping.data.db.RecentProductDBRepository
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

class ProductCataloguePresenter(private val view: ProductCatalogueContract.View) :
    ProductCatalogueContract.Presenter {
    override fun getMainProducts(): List<ProductUIModel> {
        return ProductMockData.mainProductMockData
    }

    override fun getRecentProducts(db: SQLiteDatabase): List<RecentProductUIModel> {
        val repository = RecentProductDBRepository(db)
        return repository.getAll()
    }

    override fun setProductOnClick() {
        view.showProductDetailPage()
    }
}
