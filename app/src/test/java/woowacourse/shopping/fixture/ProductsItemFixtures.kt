package woowacourse.shopping.fixture

import woowacourse.shopping.view.product.ProductsItem
import woowacourse.shopping.view.product.ProductsItem.LoadItem

val PRODUCT_ITEMS_20_MORE = PRODUCTS_COUNT_20.map { ProductsItem.ProductItem(it) } + LoadItem
val PRODUCT_ITEMS_20 = PRODUCTS_COUNT_20.map { ProductsItem.ProductItem(it) }
