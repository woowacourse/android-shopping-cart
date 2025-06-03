package woowacourse.shopping.view.product.catalog

import woowacourse.shopping.view.product.catalog.adapter.LoadMoreViewHolder
import woowacourse.shopping.view.product.catalog.adapter.ProductViewHolder
import woowacourse.shopping.view.product.catalog.adapter.recent.RecentProductViewHolder

interface ProductCatalogEventHandler :
    RecentProductViewHolder.EventHandler,
    ProductViewHolder.EventHandler,
    LoadMoreViewHolder.EventHandler
