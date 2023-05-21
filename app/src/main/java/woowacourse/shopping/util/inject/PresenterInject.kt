package woowacourse.shopping.util.inject

import android.content.Context
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.model.UiRecentProduct
import woowacourse.shopping.ui.cart.CartContract
import woowacourse.shopping.ui.cart.CartPresenter
import woowacourse.shopping.ui.detail.ProductDetailContract
import woowacourse.shopping.ui.detail.ProductDetailPresenter
import woowacourse.shopping.ui.shopping.ShoppingContract
import woowacourse.shopping.ui.shopping.ShoppingPresenter

fun inject(
    view: ShoppingContract.View,
    context: Context,
): ShoppingContract.Presenter {
    val database = createShoppingDatabase(context)
    return ShoppingPresenter(
        view,
        inject(inject(injectRecentProductDao(database))),
        inject(inject(injectCartDao(database))),
    )
}

fun inject(
    view: ProductDetailContract.View,
    detailProduct: UiProduct,
    recentProduct: UiRecentProduct?,
): ProductDetailContract.Presenter = ProductDetailPresenter(
    view = view,
    product = detailProduct,
    recentProduct = recentProduct,
)

fun inject(
    view: CartContract.View,
    context: Context,
): CartPresenter {
    val database = createShoppingDatabase(context)
    return CartPresenter(
        view,
        inject(inject(injectCartDao(database)))
    )
}
