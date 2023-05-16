package woowacourse.shopping.datas

import woowacourse.shopping.uimodel.CartProductUIModel

interface CartRepository {
    fun getAll(): List<CartProductUIModel>

    fun insert(cartProduct: CartProductUIModel)

    fun remove(cartProduct: CartProductUIModel)

    fun clear()

    fun isEmpty(): Boolean

    fun close()
}
