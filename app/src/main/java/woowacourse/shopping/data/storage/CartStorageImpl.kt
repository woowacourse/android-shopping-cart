package woowacourse.shopping.data.storage

import woowacourse.shopping.data.dao.ProductDao
import woowacourse.shopping.data.entity.ProductEntity
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import kotlin.concurrent.thread

class CartStorageImpl(private val productDao: ProductDao) : CartStorage {
    override fun insert(
        item: Product,
        onResult: (Unit) -> Unit,
    ) {
        thread {
            onResult(productDao.insert(item.toEntity()))
        }
    }

    override fun getAll(onResult: (List<Product>) -> Unit) {
        thread {
            productDao.getAll().map { it.toModel() }
        }
    }

    override fun totalSize(onResult: (Int) -> Unit) {
        thread {
            onResult(productDao.totalSize())
        }
    }

    override fun deleteProduct(
        id: Long,
        onResult: (Unit) -> Unit,
    ) {
        thread {
            onResult(productDao.deleteById(id))
        }
    }

    override fun getPaged(
        offset: Int,
        limit: Int,
        onResult: (List<Product>) -> Unit,
    ) {
        thread {
            onResult(productDao.getPaged(offset, limit).map { it.toModel() })
        }
    }

    private fun Product.toEntity() = ProductEntity(id, name, priceValue, imgUrl)

    private fun ProductEntity.toModel() = Product(id, name, Price(price), imgUrl)
}
