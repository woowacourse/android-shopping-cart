package woowacourse.shopping.data.datasource.basket

import woowacourse.shopping.data.model.DataPageNumber
import woowacourse.shopping.data.model.DataProduct

interface BasketDataSource {
    interface Local {
        fun getPartially(page: DataPageNumber): List<DataProduct>
        fun add(product: DataProduct)
        fun remove(product: DataProduct)
    }

    interface Remote
}
