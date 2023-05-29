package woowacourse.shopping.data.datasource.product

import woowacourse.shopping.data.model.DataProduct

interface ProductDataSource {
    interface Local {
        fun getPartially(size: Int, lastId: Int): List<DataProduct>
    }

    interface Remote {
        fun getPartially(size: Int, lastId: Int): List<DataProduct>
    }
}
