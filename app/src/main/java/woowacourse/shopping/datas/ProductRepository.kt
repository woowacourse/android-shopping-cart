package woowacourse.shopping.datas

import com.shopping.domain.Product

interface ProductRepository {
    fun getUnitData(unitSize: Int, pageNumber: Int): List<Product>
}
