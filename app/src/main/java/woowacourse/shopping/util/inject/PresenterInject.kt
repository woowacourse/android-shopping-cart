package woowacourse.shopping.util.inject

import android.content.Context
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.model.UiRecentProduct
import woowacourse.shopping.ui.basket.BasketContract
import woowacourse.shopping.ui.basket.BasketPresenter
import woowacourse.shopping.ui.productdetail.ProductDetailContract
import woowacourse.shopping.ui.productdetail.ProductDetailPresenter
import woowacourse.shopping.ui.shopping.ShoppingContract
import woowacourse.shopping.ui.shopping.ShoppingPresenter

fun inject(
    view: ShoppingContract.View,
    context: Context,
): ShoppingContract.Presenter {
    val database = createShoppingDatabase(context)
    return ShoppingPresenter(
        view,
        inject(inject(injectProductDao(database))),
        inject(inject(injectRecentProductDao(database))),
        inject(inject(injectBasketDao(database))),
    )
}

fun inject(
    view: ProductDetailContract.View,
    context: Context,
    detailProduct: UiProduct,
    recentProduct: UiRecentProduct?,
): ProductDetailContract.Presenter = ProductDetailPresenter(
    view = view,
    product = detailProduct,
    recentProduct = recentProduct,
    basketRepository = inject(inject(injectBasketDao(createShoppingDatabase(context)))),
)

fun inject(
    view: BasketContract.View,
    context: Context,
): BasketPresenter {
    val database = createShoppingDatabase(context)
    return BasketPresenter(
        view,
        inject(inject(injectBasketDao(database)))
    )
}
