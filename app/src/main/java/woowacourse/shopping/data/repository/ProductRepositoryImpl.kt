package woowacourse.shopping.data.repository

import woowacourse.shopping.data.local.ProductDao
import woowacourse.shopping.data.model.CartableProduct
import woowacourse.shopping.data.model.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl(private val productDao: ProductDao) : ProductRepository {
    override fun fetchSinglePage(page: Int): List<CartableProduct> {
        return productDao.getCartableProducts(page, PAGE_SIZE)
    }

    override fun fetchProduct(id: Long): CartableProduct {
        return productDao.getCartableProduct(id)
    }

    override fun addAll(products: List<Product>) {
        productDao.addAll(products)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
