package woowacourse.shopping.fixture

import woowacourse.shopping.view.product.ProductsItem
import woowacourse.shopping.view.product.ProductsItem.LoadItem

val PRODUCT_ITEMS_20_MORE = getProducts(20).map { ProductsItem.ProductItem(it) } + LoadItem
val PRODUCT_ITEMS_20 = getProducts(20).map { ProductsItem.ProductItem(it) }
