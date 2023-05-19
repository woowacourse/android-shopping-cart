package woowacourse.shopping.data.shoppingCart

import woowacourse.shopping.data.product.ProductDataSource
import woowacourse.shopping.data.product.ProductMapper.toDomainModel
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.domain.util.WoowaResult

class ShoppingCartRepositoryImpl(
    private val shoppingCartDataSource: ShoppingCartDataSource,
    private val productDataSource: ProductDataSource,
) : ShoppingCartRepository {
    override fun getShoppingCart(unit: Int, pageNumber: Int): List<ProductInCart> {
        val productInCartEntities =
            shoppingCartDataSource.getProductsInShoppingCart(unit, pageNumber)

        return productInCartEntities.mapNotNull {
            val product = productDataSource.getProductEntity(it.productId)?.toDomainModel()
                ?: return@mapNotNull null
            return@mapNotNull ProductInCart(product, it.quantity)
        }
    }

    override fun insertProductInCart(productInCart: ProductInCart): Long {
        return shoppingCartDataSource.addProductInShoppingCart(
            productInCart.product.id,
            productInCart.quantity,
        )
    }

    override fun deleteProductInCart(id: Long): Boolean {
        return shoppingCartDataSource.deleteProductInShoppingCart(id)
    }

    override fun getShoppingCartSize(): Int {
        return shoppingCartDataSource.getShoppingCartSize()
    }

    override fun getTotalQuantity(): Int {
        return shoppingCartDataSource.getTotalQuantity()
    }

    override fun updateProductCount(productId: Long, count: Int): WoowaResult<Int> {
        return shoppingCartDataSource.updateProductCount(productId, count)
    }
}
