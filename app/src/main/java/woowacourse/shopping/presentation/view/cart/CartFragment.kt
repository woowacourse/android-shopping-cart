package woowacourse.shopping.presentation.view.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentCartBinding
import woowacourse.shopping.presentation.base.BaseFragment
import woowacourse.shopping.presentation.model.ProductUiModel
import woowacourse.shopping.presentation.view.cart.adapter.CartAdapter

class CartFragment :
    BaseFragment<FragmentCartBinding>(R.layout.fragment_cart),
    CartAdapter.CartEventListener {
    private val cartAdapter: CartAdapter by lazy { CartAdapter(eventListener = this) }

    private val viewModel: CartViewModel by viewModels { CartViewModel.Factory }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        setCartAdapter()
    }

    override fun onProductDeletion(product: ProductUiModel) {
        viewModel.deleteProduct(product)
    }

    private fun setCartAdapter() {
        binding.recyclerViewCart.adapter = cartAdapter
    }

    private fun initObserver() {
        viewModel.products.observe(viewLifecycleOwner) {
            cartAdapter.updateProducts(it)
        }
    }
}
