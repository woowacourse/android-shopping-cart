package woowacourse.shopping.data.datasource.product

import woowacourse.shopping.data.model.DataProduct

interface ProductDataSource {
    interface Local {
        fun getAll(): List<DataProduct>
    }

    interface Remote
}
