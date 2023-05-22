package woowacourse.shopping.data.product

import woowacourse.shopping.data.product.ProductMapper.toDomainModel
import woowacourse.shopping.data.shoppingCart.ShoppingCartDataSource
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.util.Error
import woowacourse.shopping.domain.util.WoowaResult

class ProductRepositoryImpl(
    private val productDataSource: ProductDataSource,
    private val shoppingCartDataSource: ShoppingCartDataSource,
) : ProductRepository {

    override fun getProduct(id: Long): WoowaResult<Product> {
        val productEntity: ProductEntity =
            productDataSource.getProductEntity(id) ?: return WoowaResult.FAIL(Error.NoSuchId)

        return WoowaResult.SUCCESS(productEntity.toDomainModel())
    }

    override fun getProducts(unit: Int, lastId: Long): List<ProductInCart> {
        val products = productDataSource.getProductEntities(unit, lastId).map { productEntity ->
            productEntity.toDomainModel()
        }
        val productInCartEntities = shoppingCartDataSource.getAllEntities()
        return products.map { product ->
            val quantity = productInCartEntities.find { it.productId == product.id }?.quantity ?: 0
            ProductInCart(product, quantity)
        }
    }

    override fun isLastProduct(id: Long): Boolean {
        return productDataSource.isLastProductEntity(id)
    }
}
