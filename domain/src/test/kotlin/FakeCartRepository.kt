import com.example.domain.model.CartRepository
import com.example.domain.model.Product

class FakeCartRepository(products:List<Product> = listOf()):CartRepository {
    private val products = products.toMutableList()

    override fun getAll(): List<Product> {
        return products.toList()
    }

    override fun add(item: Product) {
        products.add(item)
    }

    override fun remove(item: Product) {
        products.remove(item)
    }
}
