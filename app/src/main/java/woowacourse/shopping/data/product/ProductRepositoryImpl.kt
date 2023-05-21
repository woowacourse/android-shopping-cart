package woowacourse.shopping.data.product

import woowacourse.shopping.data.database.dao.ProductDao
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.ShoppingProduct
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.server.ProductRemoteDataSource

class ProductRepositoryImpl(
    private val productDao: ProductDao,
    private val productRemoteDataSource: ProductRemoteDataSource
) : ProductRepository {
    override fun getProducts(startIndex: Int, size: Int): Products {
        val result = productDao.selectByRange(start = startIndex, range = size)

        if (result.value.isEmpty()) {
            val path = startIndex / size + 1
            return getProductsFromServer(path)
        }

        return result
    }

    private fun getProductsFromServer(path: Int): Products {
        val products = productRemoteDataSource.getProducts(path)
        val shoppingProducts = products.map {
            productDao.insertProduct(it)
            ShoppingProduct(product = it, amount = 0)
        }
        return Products(shoppingProducts)
    }
}
