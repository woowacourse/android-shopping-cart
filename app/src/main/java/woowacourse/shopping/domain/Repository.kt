package woowacourse.shopping.domain

import woowacourse.shopping.data.local.entity.CartProduct

interface Repository {
    fun findProductByPaging(offset: Int, pageSize: Int): Result<List<CartProduct>>

    fun findCartByPaging(offset: Int, pageSize: Int): Result<List<CartProduct>>
}