package woowacourse.shopping.data.inventory.remote

interface RemoteInventoryDataSource {
    fun fetchProduct(id: Int): ProductResponse

    fun fetchAllProducts(): ProductsResponse
}
