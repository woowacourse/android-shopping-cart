package woowacourse.shopping.data.datasource.basket

import woowacourse.shopping.data.model.DataBasketProduct

interface BasketDataSource {
    interface Local {
        fun getPreviousPartially(
            size: Int,
            standard: Int,
            includeStandard: Boolean
        ): List<DataBasketProduct>

        fun getNextPartially(
            size: Int,
            standard: Int,
            includeStandard: Boolean
        ): List<DataBasketProduct>

        fun getAll(): List<DataBasketProduct>

        fun getByProductId(productId: Int): DataBasketProduct?

        fun add(basketProduct: DataBasketProduct)

        fun minus(basketProduct: DataBasketProduct)

        fun overWriteUpdate(basketProduct: DataBasketProduct)

        fun remove(basketProduct: DataBasketProduct)
    }

    interface Remote
}
