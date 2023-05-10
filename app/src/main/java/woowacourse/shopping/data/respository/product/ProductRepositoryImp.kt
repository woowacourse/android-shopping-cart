package woowacourse.shopping.data.respository.product

import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.presentation.model.ProductModel

class ProductRepositoryImp : ProductRepository {
    override fun getData(): List<ProductModel> {
        return ProductsDao.getData().map { it.toUIModel() }
    }

    override fun getDataById(id: Long): ProductModel {
        return (ProductsDao.getDataById(id) ?: ProductsDao.getErrorData()).toUIModel()
    }
}
