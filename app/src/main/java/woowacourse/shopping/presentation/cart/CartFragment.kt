package woowacourse.shopping.presentation.cart

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartRepositoryInjector
import woowacourse.shopping.databinding.FragmentCartBinding
import woowacourse.shopping.presentation.base.BindingFragment
import woowacourse.shopping.presentation.shopping.ShoppingEventBusViewModel
import woowacourse.shopping.presentation.util.showToast

class CartFragment :
    BindingFragment<FragmentCartBinding>(R.layout.fragment_cart) {
    private lateinit var adapter: CartAdapter
    private val viewModel by viewModels<CartViewModel> {
        val cartRepository =
            CartRepositoryInjector.cartRepository(requireContext().applicationContext)
        CartViewModel.factory(cartRepository)
    }
    private val eventBusViewModel by activityViewModels<ShoppingEventBusViewModel>()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        initAppBar()
        initViews()
        initObservers()
        initErrorEvent()
    }

    private fun initAppBar() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.apply {
            title = "Cart"
            setDisplayHomeAsUpEnabled(true)
        }
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(
                    menu: Menu,
                    menuInflater: MenuInflater,
                ) {
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    if (menuItem.itemId == android.R.id.home) {
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                        return true
                    }
                    return false
                }
            },
            viewLifecycleOwner,
        )
    }

    private fun initViews() {
        adapter = CartAdapter(viewModel)
        binding?.apply {
            rvShoppingCart.adapter = adapter
        }
    }

    private fun initObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            adapter.submitList(it.products)
        }
        viewModel.updateCartEvent.observe(viewLifecycleOwner) {
            eventBusViewModel.sendUpdateCartEvent()
        }
    }

    private fun initErrorEvent() {
        viewModel.errorEvent.observe(viewLifecycleOwner) {
            when (it) {
                CartErrorEvent.LoadCartProducts -> showToast(R.string.error_msg_load_cart_products)
                CartErrorEvent.CanLoadMoreCartProducts -> showToast(R.string.error_msg_load_cart_products)
                CartErrorEvent.UpdateCartProducts -> showToast(R.string.error_msg_update_cart_products)
                CartErrorEvent.DecreaseCartCountLimit -> showToast(R.string.error_msg_decrease_cart_count_limit)
                CartErrorEvent.DeleteCartProduct -> showToast(R.string.error_msg_delete_cart_product)
            }
        }
    }

    companion object {
        val TAG: String? = CartFragment::class.java.canonicalName
    }
}
