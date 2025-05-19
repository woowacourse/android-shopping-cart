package woowacourse.shopping.presentation.view.cart

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentCartBinding
import woowacourse.shopping.presentation.base.BaseFragment
import woowacourse.shopping.presentation.model.CartItemUiModel
import woowacourse.shopping.presentation.view.cart.adapter.CartAdapter
import woowacourse.shopping.presentation.view.cart.event.CartMessageEvent

class CartFragment :
    BaseFragment<FragmentCartBinding>(R.layout.fragment_cart),
    CartAdapter.CartEventListener {
    private val cartAdapter: CartAdapter by lazy { CartAdapter(eventListener = this) }

    private val viewModel: CartViewModel by viewModels { CartViewModel.Factory }
    private val backCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateToScreen()
            }
        }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initActionBar()
        initObserver()
        setCartAdapter()

        requireActivity().onBackPressedDispatcher.addCallback(backCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        backCallback.remove()
    }

    override fun onProductDeletion(cartItem: CartItemUiModel) {
        viewModel.deleteCartItem(cartItem)
    }

    private fun initActionBar() {
        (requireActivity() as? AppCompatActivity)?.setSupportActionBar(binding.toolbarCart)

        binding.toolbarCart.setNavigationIcon(R.drawable.ic_arrow)
        binding.toolbarCart.setNavigationOnClickListener {
            navigateToScreen()
        }
    }

    private fun initObserver() {
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.cartItems.observe(viewLifecycleOwner) {
            it?.let { cartAdapter.updateItemsManually(it) }
        }

        viewModel.toastEvent.observe(viewLifecycleOwner) { event ->
            when (event) {
                CartMessageEvent.FETCH_CART_ITEMS_FAILURE -> showToast(R.string.cart_screen_event_message_fetch_cart_items_failure)
                CartMessageEvent.DELETE_CART_ITEM_FAILURE -> showToast(R.string.cart_screen_event_message_delete_cart_item_failure)
            }
        }
    }

    private fun setCartAdapter() {
        binding.recyclerViewCart.adapter = cartAdapter
    }

    private fun navigateToScreen() {
        parentFragmentManager.popBackStack()
        parentFragmentManager.commit {
            remove(this@CartFragment)
        }
    }
}
