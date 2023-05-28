package woowacourse.shopping.data.product.repository

import model.Name
import model.Price
import model.Product
import woowacourse.shopping.data.product.datasource.ProductDataSource
import woowacourse.shopping.data.product.datasource.ProductDataSourceImpl

class ProductRepositoryImpl(
    private val productDataSource: ProductDataSource = ProductDataSourceImpl(),
) : ProductRepository {

    override fun getProductById(id: Int): Product {

        return productDataSource.getProductById(id).run {
            Product(
                id = id,
                name = Name(name),
                price = Price(price),
                imageUrl = imageUrl
            )
        }
    }

    override fun getProductInRange(from: Int, count: Int): List<Product> {

        return productDataSource.getProductInRange(from, count).run {
            map {
                Product(
                    id = it.id,
                    name = Name(it.name),
                    price = Price(it.price),
                    imageUrl = it.imageUrl
                )
            }
        }
    }
}
