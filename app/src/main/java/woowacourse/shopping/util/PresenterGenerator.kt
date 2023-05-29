package woowacourse.shopping.util

import android.content.Context
import woowacourse.shopping.database.MockProductRemoteService
import woowacourse.shopping.database.MockRemoteProductRepositoryImpl
import woowacourse.shopping.database.ShoppingDBRepository
import woowacourse.shopping.database.product.ShoppingDao
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.productdetail.ProductDetailContract
import woowacourse.shopping.productdetail.ProductDetailPresenter
import woowacourse.shopping.shopping.ShoppingContract
import woowacourse.shopping.shopping.ShoppingPresenter
import woowacourse.shopping.shoppingcart.ShoppingCartContract
import woowacourse.shopping.shoppingcart.ShoppingCartPresenter

fun generateProductDetailPresenter(
    view: ProductDetailContract.View,
    product: ProductUiModel?,
    context: Context,
) = ProductDetailPresenter(
    view,
    product,
    ShoppingDBRepository(ShoppingDao(context)),
)

fun generateShoppingPresenter(
    view: ShoppingContract.View,
    context: Context,
) = ShoppingPresenter(
    view,
    ShoppingDBRepository(
        ShoppingDao(context),
    ),
    MockRemoteProductRepositoryImpl(MockProductRemoteService()),
)

fun generateShoppingCartPresenter(
    view: ShoppingCartContract.View,
    context: Context,
) = ShoppingCartPresenter(
    view,
    ShoppingDBRepository(
        ShoppingDao(context),
    ),
)
