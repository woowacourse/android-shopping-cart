package woowacourse.shopping.presentation.shopping.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.DefaultCartRepository
import woowacourse.shopping.data.recent.DefaultRecentProductRepository
import woowacourse.shopping.data.shopping.DefaultShoppingRepository
import woowacourse.shopping.databinding.FragmentProductDetailBinding
import woowacourse.shopping.presentation.base.BindingFragment
import woowacourse.shopping.presentation.navigation.ShoppingNavigator

class ProductDetailFragment :
    BindingFragment<FragmentProductDetailBinding>(R.layout.fragment_product_detail) {
    private val productId: Long by lazy { arguments?.getLong(PRODUCT_ID, -1) ?: -1 }
    override val viewModel by viewModels<ProductDetailViewModel> {
        ProductDetailViewModel.factory(
            DefaultShoppingRepository(),
            DefaultCartRepository(requireContext()),
            DefaultRecentProductRepository(requireContext()),
            productId,
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initAppBar()
        initListeners()
        initObservers()
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
                        if (viewModel.isRecentProductVisible.value == false) {
                            (requireActivity() as? ShoppingNavigator)?.navigateToProductList(1)
                        } else {
                            (requireActivity() as ShoppingNavigator).popBackStack()
                        }
                    }
                    return false
                }
            },
            viewLifecycleOwner,
        )
    }

    private fun initListeners() {
        binding?.btnProductCart?.setOnClickListener {
            viewModel.addCartProduct()
        }
        binding?.itemProductCount?.btnPlus?.setOnClickListener {
            viewModel.increaseCount()
        }
        binding?.itemProductCount?.btnMinus?.setOnClickListener {
            viewModel.decreaseCount()
        }
    }

    private fun initObservers() {
        viewModel.isAddedCart.observe(viewLifecycleOwner) { isAdded ->
            if (isAdded) {
                navigateToShoppingCart()
            }
        }
        viewModel.onClickedLastViewedProduct.observe(viewLifecycleOwner) { id ->
            (requireActivity() as? ShoppingNavigator)?.navigateToProductDetail(id)
        }
    }

    private fun navigateToShoppingCart() {
        (requireActivity() as ShoppingNavigator).navigateToCart()
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
