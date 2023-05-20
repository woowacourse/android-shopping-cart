package woowacourse.shopping.datas

import com.shopping.domain.CartProduct

interface CartRepository {
    fun getAll(): List<CartProduct>

    fun getUnitData(unitSize: Int, pageNumber: Int): List<CartProduct>

    fun updateProductIsPicked(productId: Int, isPicked: Boolean)

    fun getSize(): Int

    fun insert(cartProduct: CartProduct)

    fun remove(productId: Int)

    fun clear()

    fun isEmpty(): Boolean

    fun close()
}
