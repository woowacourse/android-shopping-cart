package woowacourse.shopping.data.database.dao.product

import woowacourse.shopping.data.model.DataProduct

interface ProductDao {
    fun getPartially(size: Int): List<DataProduct>
}
