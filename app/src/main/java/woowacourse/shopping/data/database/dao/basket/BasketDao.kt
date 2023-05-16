package woowacourse.shopping.data.database.dao.basket

import woowacourse.shopping.data.model.DataBasketProduct
import woowacourse.shopping.data.model.DataProduct

interface BasketDao {
    fun getPartiallyIncludeStartId(size: Int, standard: Int): List<DataBasketProduct>

    fun getPartiallyNotIncludeStartId(size: Int, standard: Int): List<DataBasketProduct>

    fun getPreviousPartiallyNotIncludeStartId(size: Int, standard: Int): List<DataBasketProduct>

    fun add(basketProduct: DataProduct)

    fun remove(basketProduct: DataBasketProduct)
}
