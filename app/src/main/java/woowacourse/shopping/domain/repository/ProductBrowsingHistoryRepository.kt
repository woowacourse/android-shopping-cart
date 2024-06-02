package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductBrowsingHistory

interface ProductBrowsingHistoryRepository {
    fun putProductOnHistory(product: Product)

    fun getHistories(size: Int): Result<List<ProductBrowsingHistory>>
}
