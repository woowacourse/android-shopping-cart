package woowacourse.shopping.data.state

import woowacourse.shopping.domain.Products

object ProductsState : State<Products> {
    private var products: Products = Products(emptyList())

    override fun save(t: Products) {
        products = t
    }

    override fun load(): Products {
        return products
    }
}
