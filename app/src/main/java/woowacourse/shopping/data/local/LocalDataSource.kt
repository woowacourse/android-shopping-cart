package woowacourse.shopping.data.local

import woowacourse.shopping.data.local.entity.CartEntity
import woowacourse.shopping.data.local.entity.CartProductEntity

interface LocalDataSource {

    fun findProductByPaging(offset: Int, pageSize: Int): List<CartProductEntity>

    fun findCartByPaging(offset: Int, pageSize: Int): List<CartProductEntity>

    fun saveCart(cartEntity: CartEntity): Long

    fun deleteCart(cartId: Long): Long
}