package woowacourse.shopping.data

import woowacourse.shopping.domain.Product

interface ProductDataSource {
    fun products(exceptProducts: List<Long>, amount: Int): List<Product>

    fun productById(id: Long): Product?
}