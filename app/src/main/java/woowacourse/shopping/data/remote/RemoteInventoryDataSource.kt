package woowacourse.shopping.data.remote

interface RemoteInventoryDataSource {
    fun fetchProduct(id: Int): ProductResponse

    fun fetchAllProducts(): ProductsResponse
}
