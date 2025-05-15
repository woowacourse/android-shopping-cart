package woowacourse.shopping.fixture

import woowacourse.shopping.view.product.ProductsItem
import woowacourse.shopping.view.product.ProductsItem.LoadItem

val PRODUCT_ITEMS = PRODUCTS.map { ProductsItem.ProductItem(it) } + LoadItem(true)
