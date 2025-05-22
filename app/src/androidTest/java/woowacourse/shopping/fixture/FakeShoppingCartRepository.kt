package woowacourse.shopping.fixture

import woowacourse.shopping.data.DummyShoppingCart
import woowacourse.shopping.data.ext.subList
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.data.repository.ShoppingCartRepository
import woowacourse.shopping.view.uimodel.ProductUiModel

class FakeShoppingCartRepository(
    private val data: MutableList<ProductUiModel> = DummyShoppingCart.productUiModels.toMutableList(),
) : ShoppingCartRepository {
    override fun findAll(pageRequest: PageRequest): Page<ProductUiModel> {
        val items =
            data
                .distinct()
                .subList(pageRequest)

        return pageRequest.toPage(items, totalSize())
    }

    override fun remove(productUiModel: ProductUiModel) {
        data.remove(productUiModel)
    }

    override fun totalSize(): Int {
        return TestShoppingCart.productUiModels.size
    }
}
