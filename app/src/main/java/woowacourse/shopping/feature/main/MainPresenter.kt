package woowacourse.shopping.feature.main

import com.example.data.repository.CartRepository
import com.example.data.repository.ProductRepository
import com.example.data.repository.RecentProductRepository
import com.example.domain.Product
import woowacourse.shopping.feature.list.item.ProductView.CartProductItem
import woowacourse.shopping.feature.list.item.ProductView.RecentProductsItem
import woowacourse.shopping.feature.model.mapper.toCartUi
import woowacourse.shopping.feature.model.mapper.toProductDomain
import woowacourse.shopping.feature.model.mapper.toUi
import kotlin.properties.Delegates

class MainPresenter(
    val view: MainContract.View,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository,
    private val cartRepository: CartRepository,
) : MainContract.Presenter {

    private val products: List<Product> = productRepository.requestAll()
    private var currentItemIndex by Delegates.notNull<Int>()

    override fun loadInitialData() {
        currentItemIndex = START_INDEX
        val lastIndex = if (products.size > ADD_SIZE) ADD_SIZE else products.size
        val products: MutableList<CartProductItem> =
            productRepository.requestAll().subList(currentItemIndex, lastIndex).map { product ->
                product.toCartUi()
            }.toMutableList()
        currentItemIndex = lastIndex

        val recentProducts: RecentProductsItem = recentProductRepository.getRecentProducts().toUi()
        val cartProducts = cartRepository.getAll().map { it.toUi() }

        cartProducts.forEach { cartProduct ->
            if (products.contains(cartProduct)) {
                val index = products.indexOf(cartProduct)
                products.remove(cartProduct)
                products.add(index, cartProduct)
            }
        }

        view.setInitialProducts(products, recentProducts)
    }

    override fun loadMoreProducts() {
        if (currentItemIndex >= products.lastIndex) return

        val addItems: List<Product>
        if (products.size < currentItemIndex + ADD_SIZE) {
            addItems = products.subList(currentItemIndex, products.size)
            currentItemIndex += products.lastIndex - currentItemIndex
        } else {
            addItems = products.subList(currentItemIndex, currentItemIndex + ADD_SIZE)
            currentItemIndex += ADD_SIZE
        }

        view.addProducts(addItems.map { it.toCartUi() })
    }

    override fun saveRecentProduct(product: CartProductItem) {
        val lastProduct = recentProductRepository.getLastProduct()
        recentProductRepository.addColumn(product.toProductDomain())
        view.startActivity(product, lastProduct?.toCartUi())
    }

    override fun updateProductCount(product: CartProductItem, isPlus: Boolean) {
        val changeWidth = if (isPlus) 1 else -1

        cartRepository.findProductById(product.id)?.let { cartProduct ->
            val newCartProduct = cartProduct.updateCount(cartProduct.count + changeWidth)
            cartRepository.updateColumn(newCartProduct)
            view.setProduct(newCartProduct.toUi())
        } ?: cartRepository.addColumn(product.toProductDomain(), DEFAULT_COUNT)
    }

    override fun addProduct(product: CartProductItem) {
        cartRepository.findProductById(product.id)
            ?: cartRepository.addColumn(product.toProductDomain(), DEFAULT_COUNT)
    }

    companion object {
        private const val ADD_SIZE = 20
        private const val START_INDEX = 0
        private const val DEFAULT_COUNT = 1
    }
}
