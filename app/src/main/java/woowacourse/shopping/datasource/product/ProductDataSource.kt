package woowacourse.shopping.datasource.product

import woowacourse.shopping.domain.Product

interface ProductDataSource {

    fun findAll(limit: Int, offset: Int): List<Product>

    fun countAll(): Int

    fun findById(id: Long): Product?
}