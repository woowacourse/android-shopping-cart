package woowacourse.shopping.presentation.shopping

import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.presentation.base.BindingActivity
import woowacourse.shopping.presentation.cart.CartFragment
import woowacourse.shopping.presentation.navigation.ShoppingNavigator
import woowacourse.shopping.presentation.shopping.detail.ProductDetailFragment
import woowacourse.shopping.presentation.shopping.product.ProductListFragment

class ShoppingActivity :
    BindingActivity<ActivityShoppingBinding>(R.layout.activity_shopping), ShoppingNavigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace<ProductListFragment>(
                    R.id.fragment_container_shopping,
                    ProductListFragment.TAG,
                )
            }
        }
    }

    override fun navigateToProductDetail(productId: Long) {
        supportFragmentManager.commit {
            replace<ProductDetailFragment>(
                R.id.fragment_container_shopping,
                ProductDetailFragment.TAG,
                args = ProductDetailFragment.args(productId),
            )
            addToBackStack(ProductListFragment.TAG)
        }
    }

    override fun navigateToCart() {
        supportFragmentManager.commit {
            replace<CartFragment>(
                R.id.fragment_container_shopping,
                CartFragment.TAG,
            )
            addToBackStack(ProductListFragment.TAG)
        }
    }

    override fun popBackStack(
        popUpTo: String,
        inclusive: Boolean,
    ) {
        val flag = if (inclusive) 0 else 1
        supportFragmentManager.popBackStack(popUpTo, flag)
    }

    override fun popBackStack() {
        supportFragmentManager.popBackStack()
    }
}
