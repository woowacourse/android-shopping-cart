package woowacourse.shopping.util.inject

import android.content.Context
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.ui.basket.BasketContract
import woowacourse.shopping.ui.basket.BasketPresenter
import woowacourse.shopping.ui.productdetail.ProductDetailContract
import woowacourse.shopping.ui.productdetail.ProductDetailPresenter
import woowacourse.shopping.ui.shopping.ShoppingContract
import woowacourse.shopping.ui.shopping.ShoppingPresenter

fun injectShoppingPresenter(
    view: ShoppingContract.View,
    context: Context,
): ShoppingContract.Presenter {
    val database = createShoppingDatabase(context)
    return ShoppingPresenter(
        view,
        inject(inject(injectProductDao(database))),
        inject(inject(injectRecentProductDao(database)))
    )
}

fun injectProductDetailPresenter(
    view: ProductDetailContract.View,
    context: Context,
    product: UiProduct,
): ProductDetailContract.Presenter = ProductDetailPresenter(
    view = view,
    basketRepository = inject(inject(injectBasketDao(createShoppingDatabase(context)))),
    product = product
)

fun injectBasketPresenter(
    view: BasketContract.View,
    context: Context,
): BasketContract.Presenter {
    val database = createShoppingDatabase(context)
    return BasketPresenter(view, inject(inject(injectBasketDao(database))))
}
