package woowacourse.shopping.data.repsoitory

import woowacourse.shopping.data.datasource.local.ShoppingCartDataSource
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingCartRepository

class ShoppingCartRepositoryImpl(private val dataSource: ShoppingCartDataSource) :
    ShoppingCartRepository {
    override fun insertCartProduct(
        productId: Long,
        name: String,
        price: Int,
        quantity: Int,
        imageUrl: String,
    ): Result<Unit> =
        dataSource.insertCartProduct(
            productId = productId,
            name = name,
            price = price,
            quantity = quantity,
            imageUrl = imageUrl,
        )

    override fun findCartProduct(productId: Long): Result<Product> =
        dataSource.findCartProduct(productId = productId).mapCatching { it.toDomain() }

    override fun updateCartProduct(
        productId: Long,
        quantity: Int,
    ): Result<Unit> = dataSource.updateCartProduct(productId = productId, quantity = quantity)

    override fun getCartProductsPaged(
        page: Int,
        pageSize: Int,
    ): Result<List<Product>> =
        dataSource.getCartProductsPaged(page = page, pageSize = pageSize)
            .mapCatching { result -> result.map { it.toDomain() } }

    override fun getAllCartProducts(): Result<List<Product>> =
        dataSource.getAllCartProducts().mapCatching { result -> result.map { it.toDomain() } }

    override fun getCartProductsTotal(): Result<Int> = dataSource.getCartProductsTotal()

    override fun deleteCartProduct(productId: Long): Result<Unit> = dataSource.deleteCartProduct(productId = productId)

    override fun deleteAllCartProducts(): Result<Unit> = dataSource.deleteAllCartProducts()
}
