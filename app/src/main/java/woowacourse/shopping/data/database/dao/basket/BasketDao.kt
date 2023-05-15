package woowacourse.shopping.data.database.dao.basket

import woowacourse.shopping.data.model.DataBasketProduct

interface BasketDao {
    fun getPartially(size: Int, lastId: Int, isNext: Boolean): List<DataBasketProduct>
    fun add(basketProduct: DataBasketProduct)
    fun remove(basketProduct: DataBasketProduct)
}
