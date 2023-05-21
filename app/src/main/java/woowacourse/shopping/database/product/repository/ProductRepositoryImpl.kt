package woowacourse.shopping.database.product.repository

import android.content.Context
import model.Name
import model.Price
import model.Product
import woowacourse.shopping.database.product.datasource.ProductDataSource
import woowacourse.shopping.database.product.datasource.ProductDataSourceImpl

class ProductRepositoryImpl(
    context: Context,
    private val productDataSource: ProductDataSource = ProductDataSourceImpl(context),
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

        return productDataSource.getProductInRange(from, from + count).run {
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
