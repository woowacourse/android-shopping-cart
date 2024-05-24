package woowacourse.shopping.presentation.shopping.detail

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
import woowacourse.shopping.data.shopping.ShoppingRepositoryInjector
import woowacourse.shopping.databinding.FragmentProductDetailBinding
import woowacourse.shopping.presentation.base.BindingFragment
import woowacourse.shopping.presentation.util.showToast
import woowacourse.shopping.presentation.navigation.ShoppingNavigator
import woowacourse.shopping.presentation.shopping.ShoppingEventBusViewModel

class ProductDetailFragment :
    BindingFragment<FragmentProductDetailBinding>(R.layout.fragment_product_detail) {
    private val viewModel by viewModels<ProductDetailViewModel> {
        eventBusViewModel.sendUpdateRecentProductEvent()
        val cartRepository =
            CartRepositoryInjector.cartRepository(requireContext().applicationContext)
        val shoppingRepository =
            ShoppingRepositoryInjector.shoppingRepository(requireContext().applicationContext)
        ProductDetailViewModel.factory(
            cartRepository,
            shoppingRepository,
        )
    }

    private val eventBusViewModel by activityViewModels<ShoppingEventBusViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            val id = arguments?.getLong(PRODUCT_ID, -1) ?: -1
            viewModel.loadCartProduct(id)
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding?.also {
            it.lifecycleOwner = viewLifecycleOwner
            it.vm = viewModel
            it.listener = detailItemClickListener()
        }
        initAppBar()
        initListeners()
        initObservers()
        initErrorEvent()
    }

    private fun initListeners() {
        binding?.btnProductCart?.setOnClickListener {
            viewModel.addCartProduct()
        }
    }

    private fun initObservers() {
        viewModel.addCartEvent.observe(viewLifecycleOwner) { isAdded ->
            (requireActivity() as? ShoppingNavigator)?.navigateToCart()
        }
        viewModel.recentProductEvent.observe(viewLifecycleOwner) { id ->
            (requireActivity() as? ShoppingNavigator)?.navigateToProductDetail(id)
        }
    }

    private fun initErrorEvent() {
        viewModel.errorEvent.observe(viewLifecycleOwner) { event ->
            when (event) {
                ProductDetailErrorEvent.LoadCartProduct -> showToast(R.string.error_msg_load_cart_products)
                ProductDetailErrorEvent.AddCartProduct -> showToast(R.string.error_msg_update_cart_products)
                ProductDetailErrorEvent.DecreaseCartCount -> showToast(R.string.error_msg_decrease_cart_count_limit)
                ProductDetailErrorEvent.SaveRecentProduct -> showToast(R.string.error_msg_save_recent_product)
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
                        if (viewModel.isRecentProductVisible.value == false) {
                            (requireActivity() as? ShoppingNavigator)?.navigateToProductList(1)
                        } else {
                            (requireActivity() as? ShoppingNavigator)?.popBackStack()
                        }
                    }
                    return false
                }
            },
            viewLifecycleOwner,
        )
    }

    private fun detailItemClickListener(): DetailProductListener {
        return object : DetailProductListener {
            override fun addCartProduct() {
                viewModel.addCartProduct()
                eventBusViewModel.sendUpdateCartEvent()
            }

            override fun increaseProductCount(id: Long) {
                viewModel.increaseProductCount()
            }

            override fun decreaseProductCount(id: Long) {
                viewModel.decreaseProductCount()
            }
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
