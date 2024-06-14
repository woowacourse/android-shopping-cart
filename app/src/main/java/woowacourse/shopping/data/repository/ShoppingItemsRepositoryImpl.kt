package woowacourse.shopping.data.repository

import woowacourse.shopping.data.database.product.MockProductService
import woowacourse.shopping.data.database.product.ProductDatabase
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductsRepository
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.domain.service.ProductService

class ShoppingItemsRepositoryImpl(
    productsRepository: ProductsRepository = ProductDatabase,
) : ShoppingItemsRepository {
    // private val items: List<Product> = productsRepository.products
    private val mockProductService: ProductService = MockProductService()

    /*override fun fetchProductsSize(): Int {
        return items.size
    }

    override fun fetchProductsWithIndex(
        start: Int,
        end: Int,
    ): List<Product> {
        return items.subList(start, end)
    }

    override fun getAllProducts(): List<Product> {
        return items
    }

    override fun findProductItem(id: Long): Product? {
        return items.firstOrNull { it.id == id }
    }*/

    override fun fetchProductsSize(): Int {
        var size = 0
        threadAction {
            size = mockProductService.fetchProductsSize()
        }
        return size
    }

    override fun fetchProductsWithIndex(
        start: Int,
        end: Int,
    ): List<Product> {
        var products = emptyList<Product>()
        threadAction {
            products = mockProductService.loadPagingProducts(start, end - start)
        }
        return products
    }

    override fun getAllProducts(): List<Product> {
        var products = emptyList<Product>()
        threadAction {
            products = mockProductService.findAll()
        }
        return products
    }

    override fun findProductItem(id: Long): Product? {
        var product: Product? = null
        threadAction {
            product = mockProductService.findProductById(id)
        }
        return product
    }

    private fun threadAction(action: () -> Unit) {
        val thread = Thread(action)
        thread.start()
        thread.join()
    }
}
