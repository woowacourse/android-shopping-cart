package woowacourse.shopping.data.repository

import android.os.Looper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import woowacourse.shopping.data.dataSource.local.product.ProductDataSource
import woowacourse.shopping.data.dataSource.local.recentlyViewed.RecentlyViewedDataSource
import woowacourse.shopping.data.dataSource.remote.ProductRemoteDataSource
import woowacourse.shopping.data.entity.ProductEntity
import woowacourse.shopping.data.entity.RecentlyViewedEntity
import woowacourse.shopping.data.mapper.ProductMapper.toDomainModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.util.Error
import woowacourse.shopping.domain.util.WoowaResult
import woowacourse.shopping.domain.util.WoowaResult.FAIL
import woowacourse.shopping.domain.util.WoowaResult.SUCCESS
import java.io.IOException

class ProductRepositoryImpl(
    private val productDataSource: ProductDataSource,
    private val productRemoteDataSource: ProductRemoteDataSource,
    private val recentlyViewedDataSource: RecentlyViewedDataSource,
) : ProductRepository {

    override fun getProduct(id: Long): WoowaResult<Product> {
        val productEntity: ProductEntity = getProductEntity(id) ?: return FAIL(Error.NoSuchId)

        return SUCCESS(productEntity.toDomainModel())
    }

    override fun getProducts(unit: Int, lastIndex: Int): List<Product> {
        return productDataSource.getProductEntities(unit, lastIndex).map { productEntity ->
            productEntity.toDomainModel()
        }
    }

    private fun getProducts2(unit: Int, lastIndex: Int): List<Product> {
        val temp = productRemoteDataSource.getAllProducts(unit, lastIndex)
        temp
        return productDataSource.getProductEntities(unit, lastIndex).map { productEntity ->
            productEntity.toDomainModel()
        }
    }

    private fun getProductEntity2(id: Long): ProductEntity? {
        productRemoteDataSource.getProduct(id).enqueue(
            createResponseCallback(
                onSuccess,
                onFailure
            )
        )

        return productDataSource.getProductEntity(id)
    }

    override fun getRecentlyViewedProducts(unit: Int): List<Product> {
        val recentlyViewed: List<RecentlyViewedEntity> =
            recentlyViewedDataSource.getRecentlyViewedProducts(unit)
        val productEntities: List<ProductEntity> =
            recentlyViewed.mapNotNull { getProductEntity(it.productId) }
        return productEntities.map { it.toDomainModel() }
    }

    override fun getLastViewedProduct(): WoowaResult<Product> {
        val lastViewed: List<RecentlyViewedEntity> =
            recentlyViewedDataSource.getLastViewedProduct()

        return when {
            lastViewed.isEmpty() -> FAIL(Error.NoSuchId)
            lastViewed.size > 1 -> getProductEntity(lastViewed.first().productId)?.let {
                SUCCESS(it.toDomainModel())
            } ?: FAIL(Error.NoSuchId)

            lastViewed.size == 1 -> getProductEntity(lastViewed.last().productId)?.let {
                SUCCESS(it.toDomainModel())
            } ?: FAIL(Error.NoSuchId)

            else -> throw IllegalArgumentException()
        }
    }

    private fun getProductEntity(id: Long): ProductEntity? {
        return productDataSource.getProductEntity(id)
    }

    override fun addRecentlyViewedProduct(productId: Long, unit: Int): Long {
        recentlyViewedDataSource.deleteRecentlyViewedProduct(productId)
        return recentlyViewedDataSource.addRecentlyViewedProduct(productId, unit)
    }

    private inline fun <reified T> createResponseCallback(
        crossinline onSuccess: (T) -> Unit,
        crossinline onFailure: (Exception) -> Unit,
    ): Callback {
        val handler = android.os.Handler(Looper.getMainLooper())
        return object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Thread {
                        val result = parseToObject<T>(response.body?.string())
                        handler.post {
                            onSuccess.invoke(result)
                        }
                    }.start()
                    return
                }
                handler.post {
                    onFailure.invoke(Exception("Response unsuccessful"))
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                handler.post {
                    onFailure.invoke(e)
                }
            }
        }
    }

    private inline fun <reified T> parseToObject(responseBody: String?): T {
        return Gson().fromJson(responseBody, object : TypeToken<T>() {}.type)
    }
}
