package woowacourse.shopping.data.database.dao.product

import woowacourse.shopping.data.model.Product

interface ProductDao {
    fun getPartially(size: Int, lastId: Int): List<Product>
}
