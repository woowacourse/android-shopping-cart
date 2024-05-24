package woowacourse.shopping.data.local

import woowacourse.shopping.data.local.entity.CartProduct

interface LocalDataSource {

    fun findProductByPaging(offset: Int, pageSize: Int): List<CartProduct>

    fun findCartByPaging(offset: Int, pageSize: Int): List<CartProduct>
}