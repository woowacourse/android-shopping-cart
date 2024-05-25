package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.ProductIdsCountData
import woowacourse.shopping.data.model.toDomain
import woowacourse.shopping.data.source.ProductDataSource
import woowacourse.shopping.data.source.ShoppingCartProductIdDataSource
import woowacourse.shopping.domain.model.Product

class DefaultShoppingProductRepository(
    private val productsSource: ProductDataSource,
    private val cartSource: ShoppingCartProductIdDataSource,
) : ShoppingProductsRepository {
    override fun loadAllProducts(page: Int): List<Product> {
        val productsData = productsSource.findByPaged(page)

        return productsData.map { productData ->
            productData.toDomain(productQuantity(productData.id))
        }
    }

    override fun loadProduct(id: Int): Product = productsSource.findById(id).toDomain(productQuantity(id))

    override fun isFinalPage(page: Int): Boolean = productsSource.isFinalPage(page)

    override fun shoppingCartProductQuantity(): Int =
        cartSource.loadAll().sumOf {
            it.quantity
        }

    private fun productQuantity(productId: Int) = cartSource.findByProductId(productId)?.quantity ?: 0

    override fun increaseShoppingCartProduct(id: Int) {
        cartSource.plusProductsIdCount(id)
    }

    override fun decreaseShoppingCartProduct(id: Int) {
        cartSource.minusProductsIdCount(id)
    }

    override fun addShoppingCartProduct(id: Int) {
        cartSource.addedNewProductsId(ProductIdsCountData(id, FIRST_QUANTITY))
    }

    override fun removeShoppingCartProduct(id: Int) {
        cartSource.removedProductsId(id)
    }

    companion object {
        private const val FIRST_QUANTITY = 1
    }
}
