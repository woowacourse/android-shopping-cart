package woowacourse.shopping.data

import android.util.Log
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductRepository
import java.lang.Thread.sleep

class ProductMockWebRepository : ProductRepository {
    private var products: List<Product> = mutableListOf()

    init {
        Thread {
            ProductMockWebServer().request(
                {
                    products = it
                    Log.d("HJHJ", "성공 $products")
                },
                { Log.d("HJHJ", "실패 ${it.message}") },
            )
        }.start()
        while (products.isEmpty()) sleep(1)
    }

    fun get(): List<Product> {
        // ProductMockWebServer().request({ products = it }, { })
        return products.toList()
    }

    override fun findAll(): List<Product> {
        return products
    }

    override fun find(id: Int): Product {
        return products[id]
    }

    override fun findRange(mark: Int, rangeSize: Int): List<Product> {
        if (products.size <= mark + rangeSize) {
            return products.subList(mark, products.lastIndex)
        }
        return products.subList(mark, mark + rangeSize)
    }

    override fun isExistByMark(mark: Int): Boolean {
        return products.size > mark
    }
}
