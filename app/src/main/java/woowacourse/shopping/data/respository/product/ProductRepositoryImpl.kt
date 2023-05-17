package woowacourse.shopping.data.respository.product

import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.presentation.model.ProductModel

class ProductRepositoryImpl : ProductRepository {
    override fun getData(startPosition: Int, count: Int): List<ProductModel> {
        return ProductsDao.getData(startPosition, count).map { it.toUIModel() }
    }

    override fun getDataById(id: Long): ProductModel {
        return (ProductsDao.getDataById(id) ?: ProductsDao.getErrorData()).toUIModel()
    }
}
