package woowacourse.shopping.data.shoppingCart

import woowacourse.shopping.data.product.ProductDataSource
import woowacourse.shopping.data.product.ProductMapper.toDomainModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.repository.ShoppingCartRepository

class ShoppingCartRepositoryImpl(
    private val shoppingCartDataSource: ShoppingCartDataSource,
    private val productDataSource: ProductDataSource,
) : ShoppingCartRepository {
    override fun getShoppingCart(unit: Int, pageNumber: Int): List<ProductInCart> {
        val productInCartEntities =
            shoppingCartDataSource.getProductsInShoppingCart(unit, pageNumber)

        val productsInCart = mutableListOf<ProductInCart>()
        productInCartEntities.forEach {
            val product: Product =
                (productDataSource.getProductEntity(it.productId) ?: return@forEach).toDomainModel()
            productsInCart.add(ProductInCart(product, it.quantity))
        }

        return productsInCart
    }

    override fun addProductInCart(productInCart: ProductInCart): Long {
        val productId = productInCart.product.id
        val productQuantity = productInCart.quantity

        return shoppingCartDataSource.addProductInShoppingCart(productId, productQuantity)
    }

    override fun deleteProductInCart(id: Long): Boolean {
        return shoppingCartDataSource.deleteProductInShoppingCart(id)
    }

    override fun getShoppingCartSize(): Int {
        return shoppingCartDataSource.getShoppingCartSize()
    }
}
