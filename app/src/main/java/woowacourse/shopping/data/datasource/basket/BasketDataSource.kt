package woowacourse.shopping.data.datasource.basket

import woowacourse.shopping.data.model.DataBasketProduct

interface BasketDataSource {
    interface Local {
        fun getPartially(size: Int, lastId: Int, isNext: Boolean): List<DataBasketProduct>
        fun add(basketProduct: DataBasketProduct)
        fun remove(basketProduct: DataBasketProduct)
    }

    interface Remote
}
