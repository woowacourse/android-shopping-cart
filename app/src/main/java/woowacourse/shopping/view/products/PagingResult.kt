package woowacourse.shopping.view.products

import woowacourse.shopping.domain.model.Product

data class PagingResult(val items: List<Product>, val hasNextPage: Boolean)
