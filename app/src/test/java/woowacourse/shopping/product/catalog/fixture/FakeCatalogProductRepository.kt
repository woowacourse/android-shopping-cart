package woowacourse.shopping.product.catalog.fixture

import woowacourse.shopping.data.CatalogProductRepository
import woowacourse.shopping.mapper.toUiModel
import woowacourse.shopping.product.catalog.Product
import woowacourse.shopping.product.catalog.ProductUiModel

class FakeCatalogProductRepository(
    size: Int,
) : CatalogProductRepository {
    override fun getAllProductsSize(): Int = dummyProducts.size

    override fun getProductsInRange(
        startIndex: Int,
        endIndex: Int,
    ): List<ProductUiModel> = dummyProducts.subList(startIndex, endIndex).map { it.toUiModel() }

    override fun changeProductQuantity(
        product: ProductUiModel,
        diff: Int,
    ): ProductUiModel {
        val index = dummyProducts.indexOfFirst { it.name == product.name }
        val updatedProduct: Product =
            dummyProducts[index].copy(quantity = dummyProducts[index].quantity + diff)
        dummyProducts[index] = updatedProduct
        return updatedProduct.toUiModel()
    }

    val dummyProducts =
        MutableList(size) {
            Product(
                name = "아이스 카페 아메리카노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110563]_20210426095937947.jpg",
                price = 10000,
            )
        }
}
