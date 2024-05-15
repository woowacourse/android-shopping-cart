package woowacourse.shopping.data.repsoitory

import woowacourse.shopping.data.repsoitory.DummyData.STUB_PRODUCT_A
import woowacourse.shopping.data.repsoitory.DummyData.STUB_PRODUCT_B
import woowacourse.shopping.data.repsoitory.DummyData.STUB_PRODUCT_C
import woowacourse.shopping.domain.model.PagingProduct
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductListRepository
import kotlin.math.min

object DummyProductList : ProductListRepository {
    private val productList: MutableList<Product> =
        mutableListOf(
            STUB_PRODUCT_A,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_A,
            STUB_PRODUCT_A,
            STUB_PRODUCT_A,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_A,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_A,
            STUB_PRODUCT_A,
            STUB_PRODUCT_A,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_A,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_A,
            STUB_PRODUCT_A,
            STUB_PRODUCT_A,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_A,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_A,
            STUB_PRODUCT_A,
            STUB_PRODUCT_A,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
        )

    override fun findProductById(id: Int): Result<Product> =
        runCatching {
            productList.find { it.id == id } ?: throw NoSuchElementException()
        }

    override fun getPagingProduct(
        page: Int,
        pageSize: Int,
    ): Result<PagingProduct> =
        runCatching {
            val fromIndex = page * pageSize
            val toIndex = min(fromIndex + pageSize, DummyProductList.productList.size)
            val last = toIndex == DummyProductList.productList.size
            PagingProduct(
                currentPage = page,
                productList = DummyProductList.productList.subList(fromIndex, toIndex),
                last = last,
            )
        }
}
