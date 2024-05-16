package woowacourse.shopping.presentation.cart

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.DefaultCartRepository
import woowacourse.shopping.databinding.FragmentShoppingCartBinding
import woowacourse.shopping.presentation.base.BindingFragment

class ShoppingCartFragment :
    BindingFragment<FragmentShoppingCartBinding>(R.layout.fragment_shopping_cart) {
    private lateinit var adapter: CartAdapter
    private val viewModel: ShoppingCartViewModel by lazy {
        // TODO repository 싱글톤으로 바꾸기
        ViewModelProvider(this, ShoppingCartViewModel.factory(DefaultCartRepository())).get(
            ShoppingCartViewModel::class.java,
        )
    }

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
                ) {}

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
        adapter = CartAdapter(onClickItem = { viewModel.deleteProduct(it) })
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
