package woowacourse.shopping.view.product.catalog

import woowacourse.shopping.view.product.catalog.adapter.LoadMoreViewHolder
import woowacourse.shopping.view.product.catalog.adapter.ProductViewHolder

interface ProductCatalogEventHandler :
    ProductViewHolder.EventHandler,
    LoadMoreViewHolder.EventHandler
