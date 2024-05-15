package woowacourse.shopping.presentation.shopping.cart

import android.os.Bundle
import android.view.View
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentShoppingCartBinding
import woowacourse.shopping.presentation.base.BindingFragment

class ShoppingCartFragment :
    BindingFragment<FragmentShoppingCartBinding>(R.layout.fragment_shopping_cart) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        val TAG: String? = ShoppingCartFragment::class.java.canonicalName
    }
}
