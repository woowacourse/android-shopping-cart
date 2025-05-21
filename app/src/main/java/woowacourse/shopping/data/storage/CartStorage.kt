package woowacourse.shopping.data.storage

import woowacourse.shopping.domain.Product

interface CartStorage {
    fun insert(
        item: Product,
        onResult: (Unit) -> Unit,
    )

    fun getAll(onResult: (List<Product>) -> Unit)

    fun totalSize(onResult: (Int) -> Unit)

    fun deleteProduct(
        id: Long,
        onResult: (Unit) -> Unit,
    )

    fun getPaged(
        offset: Int,
        limit: Int,
        onResult: (List<Product>) -> Unit,
    )
}
