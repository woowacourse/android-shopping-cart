package woowacourse.shopping.util.factory

import android.content.Context
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.database.dao.basket.BasketDaoImpl
import woowacourse.shopping.data.database.dao.product.ProductDaoImpl
import woowacourse.shopping.data.database.dao.recentproduct.RecentProductDaoImpl
import woowacourse.shopping.data.datasource.basket.LocalBasketDataSource
import woowacourse.shopping.data.datasource.product.LocalProductDataSource
import woowacourse.shopping.data.datasource.recentproduct.LocalRecentProductDataSource
import woowacourse.shopping.data.repository.BasketRepository
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.data.repository.RecentProductRepository
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.ui.basket.BasketContract
import woowacourse.shopping.ui.basket.BasketPresenter
import woowacourse.shopping.ui.productdetail.ProductDetailContract
import woowacourse.shopping.ui.productdetail.ProductDetailPresenter
import woowacourse.shopping.ui.shopping.ShoppingContract
import woowacourse.shopping.ui.shopping.ShoppingPresenter

fun createShoppingPresenter(
    view: ShoppingContract.View,
    database: ShoppingDatabase,
): ShoppingContract.Presenter = ShoppingPresenter(
    view,
    ProductRepository(LocalProductDataSource(ProductDaoImpl(database))),
    RecentProductRepository(
        LocalRecentProductDataSource(RecentProductDaoImpl(database))
    )
)

fun createProductDetailPresenter(
    view: ProductDetailContract.View,
    context: Context,
    product: UiProduct,
): ProductDetailContract.Presenter = ProductDetailPresenter(
    view = view,
    basketRepository =
    BasketRepository(LocalBasketDataSource(BasketDaoImpl(ShoppingDatabase(context)))),
    product = product
)

fun createBasketPresenter(
    view: BasketContract.View,
    database: ShoppingDatabase,
): BasketContract.Presenter = BasketPresenter(
    view,
    BasketRepository(LocalBasketDataSource(BasketDaoImpl(database)))
)
