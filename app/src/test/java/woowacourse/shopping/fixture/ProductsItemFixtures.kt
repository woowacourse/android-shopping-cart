package woowacourse.shopping.fixture

import woowacourse.shopping.view.product.ProductsItem.LoadItem
import woowacourse.shopping.view.product.ProductsItem.ProductItem
import woowacourse.shopping.view.product.ProductsItem.RecentWatchingItem

val PRODUCT_ITEMS_20_MORE = getProducts(20).map { ProductItem(it) } + LoadItem
val PRODUCT_ITEMS_20_MORE_AND_RECENT =
    buildList {
        add(RecentWatchingItem(RECENT_PRODUCTS))
        addAll(getProducts(20).map { ProductItem(it) })
        add(LoadItem)
    }
val PRODUCT_ITEMS_20 = getProducts(20).map { ProductItem(it) }
