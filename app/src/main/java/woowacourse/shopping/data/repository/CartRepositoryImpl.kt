package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.CartLocalDataSource
import woowacourse.shopping.data.entity.CartEntity
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.concurrent.thread

class CartRepositoryImpl(
    private val cartLocalDataSource: CartLocalDataSource,
    private val productRepository: ProductRepository,
) : CartRepository {
    override fun insert(
        item: Cart,
        onResult: () -> Unit,
    ) {
        thread {
            cartLocalDataSource.insert(item.toEntity())
            onResult()
        }
    }

    override fun getById(
        id: Long,
        onResult: (Cart?) -> Unit,
    ) {
        thread {
            val product = productRepository.getById(id)
            onResult(cartLocalDataSource.getById(id)?.toModel(product))
        }
    }

    override fun getPagedShopItems(
        offset: Int,
        limit: Int,
        onResult: (List<Cart>) -> Unit,
    ) {
        thread {
            val shopItems =
                productRepository.getProducts(offset, limit).map {
                    cartLocalDataSource.getById(it.id)?.toModel(it) ?: Cart(it, 0)
                }
            onResult(shopItems)
        }
    }

    override fun getAll(onResult: (List<Cart>) -> Unit) {
        thread {
            onResult(
                cartLocalDataSource.getAll()
                    .map { it.toModel(productRepository.getById(it.productId)) },
            )
        }
    }

    override fun totalSize(onResult: (Int) -> Unit) {
        thread {
            onResult(cartLocalDataSource.totalSize())
        }
    }

    override fun update(
        item: Cart,
        onResult: () -> Unit,
    ) {
        thread {
            cartLocalDataSource.update(item.toEntity())
            onResult()
        }
    }

    override fun deleteById(
        id: Long,
        onResult: (Unit) -> Unit,
    ) {
        thread {
            onResult(cartLocalDataSource.deleteById(id))
        }
    }

    override fun getPaged(
        offset: Int,
        limit: Int,
        onResult: (List<Cart>) -> Unit,
    ) {
        thread {
            onResult(
                cartLocalDataSource.getPaged(offset, limit)
                    .map { it.toModel(productRepository.getById(it.productId)) },
            )
        }
    }

    override fun hasOnlyPage(
        limit: Int,
        onResult: (Boolean) -> Unit,
    ) {
        thread {
            onResult(cartLocalDataSource.hasOnlyPage(limit))
        }
    }

    override fun hasNextPage(
        nextOffset: Int,
        limit: Int,
        onResult: (Boolean) -> Unit,
    ) {
        thread {
            onResult(cartLocalDataSource.getPaged(nextOffset, limit).isNotEmpty())
        }
    }

    private fun CartEntity.toModel(product: Product) = Cart(product, quantity)

    private fun Cart.toEntity() = CartEntity(product.id, quantity)
}
