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

    override fun loadProductsInCart(page: Int): List<Product> {
        val allProductIdsInCart = cartSource.loadPaged(page)
        return allProductIdsInCart.map { productIdsCountData ->
            productsSource.findById(productIdsCountData.productId).toDomain(productIdsCountData.quantity)
        }
    }

    override fun loadProduct(id: Long): Product = productsSource.findById(id).toDomain(productQuantity(id))

    override fun isFinalPage(page: Int): Boolean = productsSource.isFinalPage(page)

    override fun isCartFinalPage(page: Int): Boolean = cartSource.isFinalPage(page)

    override fun shoppingCartProductQuantity(): Int = cartSource.loadAll().sumOf { it.quantity }

    private fun productQuantity(productId: Long): Int {
        return try {
            cartSource.findByProductId(productId)?.quantity ?: 0
        } catch (e: NoSuchElementException) {
            0
        }
    }

    override fun increaseShoppingCartProduct(id: Long) {
        if (cartSource.findByProductId(id) == null) {
            addShoppingCartProduct(id)
            return
        }

        cartSource.plusProductsIdCount(id)
    }

    override fun decreaseShoppingCartProduct(id: Long) {
        val data = cartSource.findByProductId(id) ?: return

        if (data.quantity == 1) {
            removeShoppingCartProduct(id)
            return
        }

        cartSource.minusProductsIdCount(id)
    }

    override fun addShoppingCartProduct(id: Long) {
        cartSource.addedNewProductsId(ProductIdsCountData(id, FIRST_QUANTITY))
    }

    override fun removeShoppingCartProduct(id: Long) {
        cartSource.removedProductsId(id)
    }

    companion object {
        private const val FIRST_QUANTITY = 1
        private const val TAG = "DefaultShoppingProductR"
    }
}
