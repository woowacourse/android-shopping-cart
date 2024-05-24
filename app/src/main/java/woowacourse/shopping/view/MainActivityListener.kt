package woowacourse.shopping.view

import androidx.fragment.app.Fragment

interface MainActivityListener {
    fun changeFragment(nextFragment: Fragment)

    fun popFragment()

    fun observeProductList(products: (Map<Long, Int>) -> Unit)

    fun saveUpdateProduct(
        productId: Long,
        count: Int,
    )

    fun observeRecentlyProduct(reset: () -> Unit)

    fun saveUpdateRecentlyProduct()
}
