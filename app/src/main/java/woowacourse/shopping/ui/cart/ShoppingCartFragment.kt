package woowacourse.shopping.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentCartListBinding
import woowacourse.shopping.ui.FragmentNavigator
import woowacourse.shopping.ui.cart.adapter.ShoppingCartAdapter
import woowacourse.shopping.ui.cart.event.ShoppingCartError
import woowacourse.shopping.ui.cart.event.ShoppingCartEvent

class ShoppingCartFragment : Fragment() {
    private var _binding: FragmentCartListBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("FragmentCartListBinding is not initialized")

    private val viewModel: ShoppingCartViewModel by viewModels {
        DefaultShoppingCartViewModel.factory()
    }

    private val adapter: ShoppingCartAdapter by lazy {
        ShoppingCartAdapter(onCartProductListener = viewModel)
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

        observeUiState()
        observeEvent()
        observeError()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadAll()
    }

    private fun observeUiState() {
        observeItemsInCurrentPage()
    }

    private fun observeEvent() {
        viewModel.event.observe(viewLifecycleOwner) { event ->
            when (event) {
                is ShoppingCartEvent.DeleteItem -> viewModel.deleteItem(event.cartItemId)
                is ShoppingCartEvent.PopBackStack -> (requireActivity() as FragmentNavigator).popBackStack()
            }
        }
    }

    private fun observeError() {
        viewModel.error.observe(viewLifecycleOwner) { error ->
            when (error) {
                ShoppingCartError.LoadCart -> showToast(R.string.error_message_shopping_cart_products)
                ShoppingCartError.FinalPage -> showToast(R.string.error_message_shopping_cart_final_page)
                ShoppingCartError.DeleteItem -> showToast(R.string.error_message_shopping_cart_delete_item)
                ShoppingCartError.UpdateItemQuantity -> showToast(R.string.error_message_update_products_quantity_in_cart)
            }
        }
    }

    private fun showToast(@StringRes stringId: Int) {
        Toast.makeText(
            requireContext(),
            stringId,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun observeItemsInCurrentPage() {
        viewModel.uiState.itemsInCurrentPage.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    companion object {
        const val TAG = "ShoppingCartFragment"
    }
}
