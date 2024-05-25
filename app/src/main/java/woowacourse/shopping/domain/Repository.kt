package woowacourse.shopping.domain

import woowacourse.shopping.data.local.entity.CartProductEntity

interface Repository {
    fun findProductByPaging(offset: Int, pageSize: Int): Result<List<CartProduct>>

    fun findCartByPaging(offset: Int, pageSize: Int): Result<List<CartProduct>>

    fun findProductById(id: Long): Result<CartProduct?>

    fun saveCart(cart: Cart): Result<Long>

    fun deleteCart(id: Long): Result<Long>
}