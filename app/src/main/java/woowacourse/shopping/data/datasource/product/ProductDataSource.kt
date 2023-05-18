package woowacourse.shopping.data.datasource.product

import woowacourse.shopping.data.model.Product

interface ProductDataSource {
    interface Local {
        fun getPartially(size: Int, lastId: Int): List<Product>
    }

    interface Remote
}
