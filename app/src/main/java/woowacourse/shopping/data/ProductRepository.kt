package woowacourse.shopping.data

import android.util.Log
import com.domain.model.Product
import java.lang.Thread.sleep

class ProductRepository : com.domain.model.ProductRepository {

    private val webServer = ProductMockWebServer()
    private var products: List<Product> = listOf()

    init {
        Thread {
            webServer.request(
                onSuccess = {
                    products = it
                },
                onFailure = { Log.d("HKHK", "데이터 로딩에 실패했습니다 ${it.message}") },
            )
        }.start()
        if (products.isEmpty()) sleep(1000)
    }

    override fun getAll(): List<Product> {
        return products.toList()
    }

    override fun getNext(count: Int): List<Product> {
        val from = offset
        val to =
            Integer.min(offset + count, products.size)
        val subList = products.subList(from, to)
        offset = to
        return subList
    }

    override fun getUntil(offset: Int): List<Product> {
        val to = Integer.min(offset, products.size)
        val subList = products.subList(0, to)
        Companion.offset = to
        return subList
    }

    override fun findById(id: Int): Product {
        return getAll().find {
            it.id == id
        } ?: throw IllegalArgumentException("해당하는 아이템이 없습니다.")
    }

    override fun getOffset(): Int = offset

    companion object {
        var offset = 0
    }
}
