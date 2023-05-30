package woowacourse.shopping.data.recentproduct

import woowacourse.shopping.Product
import woowacourse.shopping.data.product.ProductRemoteDataSource
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentProductRepository

class RecentProductRepositoryImpl constructor(
    private val recentProductLocalDataSource: RecentProductLocalDataSource,
    productRemoteDataSource: ProductRemoteDataSource,
) : RecentProductRepository {
    private val productRepository: ProductRepository =
        ProductRepositoryImpl(productRemoteDataSource)

    override fun addRecentProductId(recentProductId: Int) {
        recentProductLocalDataSource.addRecentProduct(recentProductId)
    }

    override fun deleteRecentProductId(recentProductId: Int) {
        recentProductLocalDataSource.deleteRecentProduct(recentProductId)
    }

    override fun getRecentProducts(size: Int, onSuccess: (List<Product>) -> Unit) {
        val recentProductIdList = recentProductLocalDataSource.getRecentProductIdList(size)
        val productList = mutableListOf<Product>()
        getProduct(0, productList, recentProductIdList, onSuccess)
    }

    private fun getProduct(
        index: Int,
        productList: MutableList<Product>,
        recentProductIdList: List<Int>,
        onSuccess: (List<Product>) -> Unit,
    ) {
        if (recentProductIdList.isEmpty()) return
        productRepository.findProductById(id = recentProductIdList[index]) {
            if (it != null) productList.add(it)
            if (index == recentProductIdList.lastIndex) onSuccess(productList)
            if (index + 1 <= recentProductIdList.lastIndex) {
                getProduct(
                    index + 1,
                    productList,
                    recentProductIdList,
                    onSuccess,
                )
            }
        }
    }

    override fun getMostRecentProduct(onSuccess: (Product) -> Unit) {
        val mostRecentProductId = recentProductLocalDataSource.getMostRecentProductId()
        return productRepository.findProductById(id = mostRecentProductId) {
            if (it != null) onSuccess(it)
        }
    }
}
