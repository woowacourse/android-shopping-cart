package woowacourse.shopping.data.datasource.basket

import woowacourse.shopping.data.model.DataBasketProduct
import woowacourse.shopping.data.model.DataProduct

interface BasketDataSource {
    interface Local {
        fun getPartially(
            size: Int,
            standard: Int,
            isNext: Boolean,
            includeStandard: Boolean
        ): List<DataBasketProduct>

        fun add(basketProduct: DataProduct)
        fun remove(basketProduct: DataBasketProduct)
    }

    interface Remote
}
