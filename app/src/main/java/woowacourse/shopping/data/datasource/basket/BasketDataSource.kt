package woowacourse.shopping.data.datasource.basket

import woowacourse.shopping.data.model.DataProduct

interface BasketDataSource {
    interface Local {
        fun getPartially(size: Int, lastId: Int, isNext: Boolean): List<DataProduct>
        fun add(product: DataProduct)
        fun remove(product: DataProduct)
    }

    interface Remote
}
