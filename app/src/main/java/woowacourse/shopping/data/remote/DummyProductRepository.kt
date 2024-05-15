package woowacourse.shopping.data.remote

import android.util.Log
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.presentation.ui.Product
import kotlin.math.min

class DummyProductRepository : ProductRepository {
    val products =
        List(51) { id -> // 0부터 50까지의 id를 가진 Product 리스트 생성
            Product(
                id = id.toLong(),
                imgUrl = "https://image.utoimage.com/preview/cp872722/2022/12/202212008462_500.jpg",
                name = "$id", // id 값을 String으로 변환하여 name에 할당
                price = 10000,
            )
        }

    override fun load(
        pageOffset: Int,
        pageSize: Int,
    ): Result<List<Product>> =
        runCatching {
            val startIndex = pageOffset * pageSize
            val endIndex = min(startIndex + pageSize, products.size)
            Log.d("테스트", "$startIndex,$endIndex")
            products.subList(startIndex, endIndex)
        }

    override fun loadById(id: Long): Result<Product> =
        runCatching {
            products.first { it.id == id }
        }
}
