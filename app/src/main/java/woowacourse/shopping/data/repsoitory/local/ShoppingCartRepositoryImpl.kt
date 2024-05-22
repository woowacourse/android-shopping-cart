package woowacourse.shopping.data.repsoitory.local

import woowacourse.shopping.data.dao.ShoppingCartDao
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.data.model.local.CartProductEntity
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.local.ShoppingCartRepository

class ShoppingCartRepositoryImpl(private val dao: ShoppingCartDao) : ShoppingCartRepository {
    override fun insertCartProduct(
        productId: Long,
        name: String,
        price: Int,
        quantity: Int,
        imageUrl: String,
    ): Result<Unit> =
        runCatching {
            val cartProductEntity =
                CartProductEntity(
                    productId = productId,
                    name = name,
                    price = price,
                    quantity = quantity,
                    imageUrl = imageUrl,
                )
            dao.insertCartProduct(cartProductEntity = cartProductEntity)
        }

    override fun findCartProduct(productId: Long): Result<Product> =
        runCatching {
            dao.findCartProduct(productId = productId).toDomain()
        }

    override fun updateCartProduct(
        productId: Long,
        quantity: Int,
    ): Result<Unit> =
        runCatching {
            dao.updateCartProduct(productId = productId, quantity = quantity)
        }

    override fun getCartProductsPaged(
        page: Int,
        pageSize: Int,
    ): Result<List<Product>> =
        runCatching {
            dao.getCartProductsPaged(page = page, pageSize = pageSize).map { it.toDomain() }
        }

    override fun getAllCartProducts(): Result<List<Product>> =
        runCatching {
            dao.getAllCartProducts().map { it.toDomain() }
        }

    override fun getCartProductsTotal(): Result<Int> =
        runCatching {
            dao.getCartProductsTotal()
        }

    override fun deleteCartProduct(productId: Long): Result<Unit> =
        runCatching {
            dao.deleteCartProduct(productId = productId)
        }

    override fun deleteAllCartProducts(): Result<Unit> =
        runCatching {
            dao.deleteAllCartProduct()
        }
}
