package woowacourse.shopping.data.repository

import woowacourse.shopping.data.remote.datasource.ProductRemoteDataSource
import woowacourse.shopping.data.remote.exception.NetworkException
import woowacourse.shopping.data.remote.mapper.toDomain
import woowacourse.shopping.domain.exception.ShoppingException
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.Products
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.math.min

class ProductRepositoryImpl(
    private val productRemoteDataSource: ProductRemoteDataSource,
) : ProductRepository {
    private var cachedProducts: Products? = null

    override suspend fun getProducts(): Products {
        cachedProducts?.let { return it }
        return try {
            val remoteProducts =
                Products(
                    productRemoteDataSource.getProducts().map { it.toDomain() },
                )

            cachedProducts = remoteProducts
            remoteProducts
        } catch (e: NetworkException) {
            throw when (e.code) {
                404 -> ShoppingException.NotFoundException("상품을 찾을 수 없습니다.")
                in 500..599 -> ShoppingException.ServerException("서버 오류가 발생했습니다.")
                else -> ShoppingException.ConnectionException("네트워크 오류가 발생했습니다.")
            }
        } catch (_: Exception) {
            throw ShoppingException.ConnectionException("알 수 없는 오류가 발생했습니다.")
        }
    }

    override suspend fun getPagingProducts(
        page: Int,
        pageSize: Int,
    ): Products {
        if (page < 0 || pageSize <= 0) return Products()

        val products = getProducts().productItems
        val fromIndex = page * pageSize

        if (fromIndex >= products.size) {
            return Products()
        }

        val toIndex = min(fromIndex + pageSize, products.size)
        return Products(products.subList(fromIndex, toIndex))
    }

    override suspend fun hasNextPage(
        currentPage: Int,
        pageSize: Int,
    ): Boolean {
        val products = getProducts().productItems
        val nextPageStartIndex = (currentPage + 1) * pageSize
        return nextPageStartIndex < products.size
    }

    override suspend fun findProductById(productId: Int): Product? =
        try {
            productRemoteDataSource
                .getProduct(productId)
                .toDomain()
        } catch (e: NetworkException) {
            throw when (e.code) {
                404 -> ShoppingException.NotFoundException("상품을 찾을 수 없습니다.")
                in 500..599 -> ShoppingException.ServerException("서버 오류가 발생했습니다.")
                else -> ShoppingException.ConnectionException("네트워크 오류가 발생했습니다.")
            }
        } catch (_: Exception) {
            throw ShoppingException.ConnectionException("알 수 없는 오류가 발생했습니다.")
        }
}
