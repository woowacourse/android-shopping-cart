package woowacourse.shopping.data.product

import woowacourse.shopping.Price
import woowacourse.shopping.Product

object MockProductRepository : ProductRepository {

    override val products: List<Product> = getProducts(100)

    private fun getProducts(size: Int): List<Product> {
        return (1..size).map {
            val randomNumber = (1..200).random()
            Product(
                it,
                "https://mediahub.seoul.go.kr/uploads/2016/09/952e8925ec41cc06e6164d695d776e51.jpg",
                name = "상품$it",
                price = Price((1000..100000).random()),
            )
        }
    }

    override fun findProductById(id: Int): Product? = products.find { it.id == id }
}
