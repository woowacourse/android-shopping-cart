package woowacourse.shopping.local.datasource

import woowacourse.shopping.data.datasource.local.ShoppingCartDataSource
import woowacourse.shopping.data.model.local.CartProductDto
import woowacourse.shopping.local.dao.ShoppingCartDao
import woowacourse.shopping.local.mapper.toData
import woowacourse.shopping.local.model.CartProductEntity
import java.time.LocalDateTime

class ShoppingCartDataSourceImpl(private val dao: ShoppingCartDao) : ShoppingCartDataSource {
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
                    createAt = LocalDateTime.now(),
                )
            dao.insertCartProduct(cartProductEntity = cartProductEntity)
        }

    override fun findCartProduct(productId: Long): Result<CartProductDto> =
        runCatching {
            dao.findCartProduct(productId = productId).toData()
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
    ): Result<List<CartProductDto>> =
        runCatching {
            dao.getCartProductsPaged(page = page, pageSize = pageSize).map { it.toData() }
        }

    override fun getAllCartProducts(): Result<List<CartProductDto>> =
        runCatching {
            dao.getAllCartProducts().map { it.toData() }
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
