package woowacourse.shopping.data.recentviewed.repository

import model.Name
import model.Price
import model.Product
import model.RecentViewedProduct
import woowacourse.shopping.data.product.datasource.ProductDataSource
import woowacourse.shopping.data.product.datasource.ProductDataSourceImpl
import woowacourse.shopping.data.recentviewed.datasource.RecentViewedProductDataSource

class RecentViewedProductRepositoryImpl(
    private val recentViewedProductDataSource: RecentViewedProductDataSource,
    private val productDataSource: ProductDataSource = ProductDataSourceImpl(),
) : RecentViewedProductRepository {

    override fun addToRecentViewedProduct(id: Int) {
        recentViewedProductDataSource.addToRecentViewedProduct(id)
    }

    override fun getRecentViewedProducts(): List<RecentViewedProduct> {

        return recentViewedProductDataSource.getRecentViewedProducts().map {
            val product = productDataSource.getProductById(it.id)

            RecentViewedProduct(
                id = it.id,
                imageUrl = product.imageUrl,
                name = Name(product.name)
            )
        }
    }

    override fun removeRecentViewedProduct() {
        recentViewedProductDataSource.removeRecentViewedProduct()
    }

    override fun getRecentViewedProductById(id: Int): RecentViewedProduct {

        return productDataSource.getProductById(id).run {
            RecentViewedProduct(
                id = id,
                name = Name(name),
                imageUrl = imageUrl
            )
        }
    }

    override fun getLatestViewedProduct(): Product? {

        return recentViewedProductDataSource.getLatestViewedProduct()?.run {
            val product = productDataSource.getProductById(id)

            Product(
                id = product.id,
                name = Name(product.name),
                price = Price(product.price),
                imageUrl = product.imageUrl
            )
        }
    }
}
