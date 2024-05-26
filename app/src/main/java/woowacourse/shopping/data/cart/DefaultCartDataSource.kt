package woowacourse.shopping.data.cart

import woowacourse.shopping.data.cart.model.CartData
import woowacourse.shopping.domain.entity.CartProduct
import woowacourse.shopping.local.dao.CartDao
import woowacourse.shopping.local.entity.CartEntity
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit

class DefaultCartDataSource(
    private val executors: ExecutorService,
    private val cartDao: CartDao,
) : CartDataSource {
    override fun loadCarts(
        currentPage: Int,
        productSize: Int,
    ): Result<List<CartData>> {
        return runCatching {
            executors.submit(
                Callable {
                    cartDao.loadCart((currentPage - 1) * productSize, productSize)
                        .map(CartEntity::toData)
                },
            )[TIME_OUT, TimeUnit.SECONDS]
        }
    }

    override fun filterCartProducts(ids: List<Long>): Result<List<CartData>> {
        return runCatching {
            executors.submit(
                Callable {
                    cartDao.filterCartProducts(ids).map(CartEntity::toData)
                },
            )[TIME_OUT, TimeUnit.SECONDS]
        }
    }

    override fun addCartProduct(product: CartProduct): Result<Long> {
        return runCatching {
            executors.submit(
                Callable {
                    cartDao.saveCart(product.toEntity())
                },
            )[TIME_OUT, TimeUnit.SECONDS]
        }
    }

    override fun deleteCartProduct(productId: Long): Result<Long> {
        return runCatching {
            executors.submit(
                Callable {
                    cartDao.deleteCart(productId).toLong()
                },
            )[TIME_OUT, TimeUnit.SECONDS]
        }
    }

    override fun canLoadMoreCart(size: Int): Result<Boolean> {
        return runCatching {
            executors.submit(
                Callable {
                    cartDao.canLoadMore(size)
                },
            )[TIME_OUT, TimeUnit.SECONDS]
        }
    }

    companion object {
        private const val TIME_OUT = 10L
    }
}
