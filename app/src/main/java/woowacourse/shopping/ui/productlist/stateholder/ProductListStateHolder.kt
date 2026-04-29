package woowacourse.shopping.ui.productlist.stateholder

import androidx.compose.runtime.mutableStateListOf
import woowacourse.shopping.constants.MockData
import woowacourse.shopping.domain.Product
import woowacourse.shopping.ui.state.ProductUiModel

class ProductListStateHolder {
    private val _products = mutableStateListOf<Product>()
    val products: List<Product> = _products

    fun getAllProducts(): List<Product> {

        if (_products.size % PAGE_SIZE != 0) {
            return products
        }
        _products.addAll(MockData.MOCK_PRODUCTS.take(_products.size + PAGE_SIZE))

        return products
    }

    fun toProductUiModel(product: Product): ProductUiModel {
        return ProductUiModel.of(
            name = product.name,
            price = product.priceAmount(),
            imageUrl = product.imageUrl,
            id = product.id,
        )
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}
