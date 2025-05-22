package woowacourse.shopping.view.products

import woowacourse.shopping.model.products.Product

interface ProductsClickListener {
    fun onClick(product: Product)
}
