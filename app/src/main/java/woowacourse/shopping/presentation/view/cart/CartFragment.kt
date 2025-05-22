package woowacourse.shopping.presentation.view.cart

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initActionBar()
        initObserver()
        setCartAdapter()
    }

    override fun onProductDeletion(cartItem: CartItemUiModel) {
        viewModel.deleteCartItem(cartItem)
    }

    override fun increaseQuantity(productId: Long) {
        viewModel.addProductToCart(productId)
    }

    override fun decreaseQuantity(productId: Long) {
        viewModel.removeProductFromCart(productId)
    }

    private fun initActionBar() {
        (requireActivity() as? AppCompatActivity)?.setSupportActionBar(binding.toolbarCart)

        binding.toolbarCart.setNavigationIcon(R.drawable.ic_arrow)
        binding.toolbarCart.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun initObserver() {
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            cartAdapter.updateItemsManually(cartItems)
        }

        viewModel.toastEvent.observe(viewLifecycleOwner) { event ->
            showToast(event.toMessageResId())
        }
    }

    private fun setCartAdapter() {
        binding.recyclerViewCart.adapter = cartAdapter
    }

    private fun CartMessageEvent.toMessageResId(): Int =
        when (this) {
            CartMessageEvent.FETCH_CART_ITEMS_FAILURE ->
                R.string.cart_screen_event_message_fetch_cart_items_failure

            CartMessageEvent.DELETE_CART_ITEM_FAILURE ->
                R.string.cart_screen_event_message_delete_cart_item_failure

            CartMessageEvent.INCREASE_PRODUCT_TO_CART_FAILURE ->
                R.string.cart_screen_event_message_increase_cart_item_count_failure

            CartMessageEvent.DECREASE_PRODUCT_FROM_CART_FAILURE ->
                R.string.cart_screen_event_message_decrease_cart_item_count_failure

            CartMessageEvent.FIND_PRODUCT_QUANTITY_FAILURE ->
                R.string.cart_screen_event_message_find_quantity_failure
        }
}
