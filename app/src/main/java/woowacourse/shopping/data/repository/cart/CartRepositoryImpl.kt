package woowacourse.shopping.data.repository.cart

import kotlin.collections.map
import woowacourse.shopping.data.local.cart.CartDao
import woowacourse.shopping.data.local.cart.CartItemEntity
import woowacourse.shopping.data.repository.product.ProductRepository
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartContent
import woowacourse.shopping.domain.Money
import woowacourse.shopping.domain.Product

class CartRepositoryImpl(
    private val cartDao: CartDao,
    private val productRepository: ProductRepository,
) : CartRepository {

    override suspend fun loadCart(): Cart {
        val contents = cartDao.findAll().map { it.toCartContent() }
        return Cart(contents)
    }

    override suspend fun loadCartContents(): List<CartContent> {
        val contents = cartDao.findAll().map { it.toCartContent() }
        return contents
    }

    override suspend fun loadCartSize(): Int {
        return cartDao.findAll().size
    }

    override suspend fun increase(product: Product) {
        increase(product, 1)
    }

    override suspend fun decrease(productId: String) {
        val existing = cartDao.findById(productId)
            ?: return
        if (existing.quantity <= 1) {
            cartDao.deleteById(productId)
        } else {
            cartDao.update(existing.copy(quantity = existing.quantity - 1))
        }
    }

    override suspend fun pagination(
        startIndex: Int,
        pageSize: Int,
    ): List<CartContent> {
        return cartDao.pagination(startIndex, pageSize).map { it.toCartContent() }
    }

    override suspend fun remove(productId: String) {
        cartDao.deleteById(productId)
    }

    override suspend fun setProductQuantity(
        productId: String,
        quantity: Int,
    ) {
        val existing = cartDao.findById(productId)
        if (quantity < 1) return
        if (existing != null) {
            cartDao.update(existing.copy(quantity = quantity))
        } else {
            val product = productRepository.getProduct(productId)
            cartDao.insert(product.toEntity(quantity = quantity))
        }
    }

    private suspend fun increase(
        product: Product,
        quantity: Int,
    ) {
        val existing = cartDao.findById(product.id)
        if (existing == null) {
            cartDao.insert(product.toEntity(quantity = quantity))
        } else {
            cartDao.update(existing.copy(quantity = existing.quantity + quantity))
        }
    }

    private fun CartItemEntity.toCartContent(): CartContent = CartContent(
        product = Product(
            name = name,
            price = Money(priceAmount),
            imageUrl = imageUrl,
            id = productId,
        ),
        quantity = quantity,
    )

    private fun Product.toEntity(quantity: Int): CartItemEntity = CartItemEntity(
        productId = id,
        name = name,
        priceAmount = priceAmount(),
        imageUrl = imageUrl,
        quantity = quantity,
    )
}
