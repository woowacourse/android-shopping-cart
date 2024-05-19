package woowacourse.shopping.presentation.shopping.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.DefaultCartRepository
import woowacourse.shopping.data.shopping.DefaultShoppingRepository
import woowacourse.shopping.databinding.FragmentProductDetailBinding
import woowacourse.shopping.presentation.base.BindingFragment
import woowacourse.shopping.presentation.cart.ShoppingCartFragment

class ProductDetailFragment :
    BindingFragment<FragmentProductDetailBinding>(R.layout.fragment_product_detail) {
    override val viewModel by viewModels<ProductDetailViewModel> {
        ProductDetailViewModel.factory(
            DefaultShoppingRepository(),
            DefaultCartRepository(),
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getLong(PRODUCT_ID, -1) ?: -1
        viewModel.loadProduct(id)
        initAppBar()
        binding?.btnProductCart?.setOnClickListener {
            viewModel.addCartProduct()
        }
        viewModel.isAddedCart.observe(viewLifecycleOwner) { isAdded ->
            if (isAdded) {
                navigateToShoppingCart()
                // TODO: SINGLE LIVEDATA 로 개선해보자
                viewModel.addCartDone()
            }
        }
    }

    private fun initAppBar() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.apply {
            title = ""
            setDisplayHomeAsUpEnabled(false)
        }
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(
                    menu: Menu,
                    menuInflater: MenuInflater,
                ) {
                    menuInflater.inflate(R.menu.detail_product_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    if (menuItem.itemId == R.id.menu_item_close) {
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                        return true
                    }
                    return false
                }
            },
            viewLifecycleOwner,
        )
    }

    private fun navigateToShoppingCart() {
        parentFragmentManager.commit {
            replace<ShoppingCartFragment>(
                R.id.fragment_container_shopping,
                ShoppingCartFragment.TAG,
            )
            addToBackStack(TAG)
        }
    }

    companion object {
        val TAG: String? = ProductDetailFragment::class.java.canonicalName
        const val PRODUCT_ID = "PRODUCT_ID"

        fun args(id: Long): Bundle =
            Bundle().apply {
                putLong(PRODUCT_ID, id)
            }
    }
}
