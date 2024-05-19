package woowacourse.shopping.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.FiveCartItemPagingStrategy
import woowacourse.shopping.ShoppingCartViewModelFactory
import woowacourse.shopping.databinding.FragmentCartListBinding
import woowacourse.shopping.repository.DummyShoppingCartItemRepository

class CartFragment : Fragment() {
    private var _binding: FragmentCartListBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("FragmentCartListBinding is not initialized")

    private val factory = ShoppingCartViewModelFactory(DummyShoppingCartItemRepository(FiveCartItemPagingStrategy()))

    private val viewModel: ShoppingCartViewModel by lazy {
        ViewModelProvider(this, factory)[ShoppingCartViewModel::class.java]
    }

    private val adapter: CartItemRecyclerViewAdapter by lazy {
        CartItemRecyclerViewAdapter(
            emptyList(),
            onClick = { cartItemId ->
                viewModel.deleteItem(cartItemId)
            },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCartListBinding.inflate(inflater)
        binding.cartList.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = this

        initNavigation()
        observeItemsInCurrentPage()
    }

    private fun initNavigation() {
        binding.productDetailToolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun observeItemsInCurrentPage() {
        viewModel.itemsInCurrentPage.observe(viewLifecycleOwner) { products ->
            adapter.updateData(products)
        }
    }
}
