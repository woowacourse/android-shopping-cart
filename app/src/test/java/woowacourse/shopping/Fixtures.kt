package woowacourse.shopping

import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.ShoppingProductModel
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.URL
import java.time.LocalDateTime

fun createProductModel(): ProductModel = ProductModel("", "", 1000)
fun createShoppingProductModel(): ShoppingProductModel =
    ShoppingProductModel(createProductModel(), 1)
fun createCartProductModel(): CartProductModel =
    CartProductModel(LocalDateTime.now(), 0, true, createProductModel())

fun createProduct(): Product = Product(URL(""), "글로", 1000)
fun createRecentProduct(): RecentProduct =
    RecentProduct(LocalDateTime.now(), createProduct())
fun createCartProduct(): CartProduct = CartProduct(LocalDateTime.now(), 0, true, createProduct())
