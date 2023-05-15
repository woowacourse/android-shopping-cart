package woowacourse.shopping.data.database.dao.basket

import woowacourse.shopping.data.model.DataBasketProduct
import woowacourse.shopping.data.model.DataProduct

interface BasketDao {
    fun getPartially(size: Int, lastId: Int, isNext: Boolean): List<DataBasketProduct>
    fun add(basketProduct: DataProduct)
    fun remove(basketProduct: DataBasketProduct)
}
