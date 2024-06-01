package woowacourse.shopping.view.products

import woowacourse.shopping.domain.model.ProductWithQuantity

data class PagingResult(val items: List<ProductWithQuantity>, val hasNextPage: Boolean)
