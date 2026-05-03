package woowacourse.shopping.ui.productdetail.stateholder

import woowacourse.shopping.constants.MockData
import woowacourse.shopping.domain.Product
import woowacourse.shopping.ui.state.ProductUiModel

class ProductDetailStateHolder {
    fun getProductUiModel(id: String?): ProductUiModel? {
        val product = getProductById(id) ?: return null
        return toProductUiModel(product)
    }

    private fun getProductById(id: String?): Product? {
        val product = MockData.MOCK_PRODUCTS.firstOrNull { it.id == id }
        return product
    }

    private fun toProductUiModel(product: Product): ProductUiModel = ProductUiModel.of(
        name = product.name,
        price = product.priceAmount(),
        imageUrl = product.imageUrl,
        id = product.id,
    )
}
