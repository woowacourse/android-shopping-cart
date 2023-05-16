package woowacourse.shopping.datas

import woowacourse.shopping.uimodel.CartProductUIModel

interface CartRepository {
    fun getAll(): List<CartProductUIModel>

    fun getUnitData(unitSize: Int, pageNumber: Int): List<CartProductUIModel>

    fun getSize(): Int

    fun insert(cartProduct: CartProductUIModel)

    fun remove(cartProduct: CartProductUIModel)

    fun clear()

    fun isEmpty(): Boolean

    fun close()
}
