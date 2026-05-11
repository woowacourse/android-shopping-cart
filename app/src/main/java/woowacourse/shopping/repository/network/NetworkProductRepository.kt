package woowacourse.shopping.repository.network

import org.json.JSONArray
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.network.NetworkClient
import woowacourse.shopping.repository.ProductRepository
import java.util.UUID

class NetworkProductRepository(
    private val networkClient: NetworkClient,
) : ProductRepository {
    private var cachedProducts: List<Product>? = null

    override suspend fun getSize(): Int = fetchAndParseProducts().size

    override suspend fun getProducts(
        fromIndex: Int,
        count: Int,
    ): List<Product> {
        val allProducts = fetchAndParseProducts()
        val safeFromIndex = fromIndex.coerceIn(0, allProducts.size)
        return allProducts.drop(safeFromIndex).take(count)
    }

    override suspend fun hasNext(currentIndex: Int): Boolean {
        val total = getSize()
        return currentIndex < total - 1
    }

    override suspend fun findProduct(id: UUID): Product? = fetchAndParseProducts().find { it.id == id }

    private suspend fun fetchAndParseProducts(): List<Product> {
        cachedProducts?.let { return it }

        val baseUrl = ShoppingApplication.baseUrl
        val jsonString = networkClient.getProducts(baseUrl)
        val jsonArray = JSONArray(jsonString)
        val products = mutableListOf<Product>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)

            products.add(
                Product(
                    id = UUID.fromString(jsonObject.getString("id")),
                    name = jsonObject.getString("name"),
                    price = Money(jsonObject.getInt("price")),
                    imageUrl = jsonObject.getString("imageUrl"),
                ),
            )
        }
        cachedProducts = products
        return products
    }
}
