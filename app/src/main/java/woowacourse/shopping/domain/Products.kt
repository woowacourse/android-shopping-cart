package woowacourse.shopping.domain

import woowacourse.shopping.ProductFixture
import kotlin.math.min
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

val SHOPPING_PAGE_SIZE = 20
data class Products(
    val products: List<Product> = emptyList()
) {
    fun getProducts(page:Int, pageSize:Int = SHOPPING_PAGE_SIZE):List<Product>{
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + SHOPPING_PAGE_SIZE, products.size)
        return products.subList(0,toIndex)
    }

    fun hasNextPage(currentPage:Int,pageSize:Int = SHOPPING_PAGE_SIZE) : Boolean{
        return getProducts(page = currentPage).size >= SHOPPING_PAGE_SIZE &&
                (currentPage+1) * pageSize < products.size
    }
    @OptIn(ExperimentalUuidApi::class)
    fun findProductById(productId: Uuid): Product? = ProductFixture.productList.firstOrNull { it.productId == productId }


}