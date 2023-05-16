package woowacourse.shopping.datas

import woowacourse.shopping.uimodel.ProductUIModel

interface ProductRepository {
    fun getUnitData(unitSize: Int, pageNumber: Int): List<ProductUIModel>
}
