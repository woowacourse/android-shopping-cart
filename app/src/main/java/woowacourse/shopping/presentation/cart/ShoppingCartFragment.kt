package woowacourse.shopping.presentation.cart

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.data.DefaultShoppingRepository
import woowacourse.shopping.databinding.FragmentShoppingCartBinding
import woowacourse.shopping.presentation.base.BindingFragment

class ShoppingCartFragment :
    BindingFragment<FragmentShoppingCartBinding>(R.layout.fragment_shopping_cart) {
    private lateinit var adapter: CartAdapter
    private val viewModel: ShoppingCartViewModel by lazy {
        // TODO repository 싱글톤으로 바꾸기
        ViewModelProvider(this, ShoppingCartViewModel.factory(DefaultShoppingRepository())).get(
            ShoppingCartViewModel::class.java
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        adapter = CartAdapter(onClickItem = { viewModel.deleteProduct(it)})
        binding?.apply {
            rvShoppingCart.adapter = adapter
        }
    }

    private fun initObservers() {
        viewModel.products.observe(viewLifecycleOwner) {
            adapter.updateProduct(it)
        }
    }

    companion object {
        val TAG: String? = ShoppingCartFragment::class.java.canonicalName
    }
}

