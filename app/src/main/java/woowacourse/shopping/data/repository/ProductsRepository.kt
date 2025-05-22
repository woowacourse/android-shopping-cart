package woowacourse.shopping.data.repository

import woowacourse.shopping.data.page.Page
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.view.uimodel.ProductUiModel

interface ProductsRepository {
    fun findAll(pageRequest: PageRequest): Page<ProductUiModel>

    fun totalSize(): Int
}
