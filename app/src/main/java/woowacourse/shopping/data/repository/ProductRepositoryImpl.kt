package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.page.Page
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl : ProductRepository {
    override fun getProductInRange(start: Page, end: Page): List<Product> =
        insertDummies(30)

    override fun getProductByPage(page: Page): List<Product> = insertDummies(30)

    companion object {
        fun insertDummies(size: Int): List<Product> = (0 until size).map { id ->
            Product(
                id,
                "name $id",
                Price(1000),
                "https://image.istarbucks.co.kr/upload/store/skuimg/2021/02/[9200000001939]_20210225094313315.jpg"
            )
        }
    }
}
