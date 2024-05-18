package woowacourse.shopping.presentation.shopping.product

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
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.data.shopping.DefaultShoppingRepository
import woowacourse.shopping.databinding.FragmentProductListBinding
import woowacourse.shopping.presentation.base.BindingFragment
import woowacourse.shopping.presentation.cart.ShoppingCartFragment
import woowacourse.shopping.presentation.shopping.detail.ProductDetailFragment
import woowacourse.shopping.presentation.shopping.product.adpater.ProductAdapter
import woowacourse.shopping.presentation.util.dp

class ProductListFragment :
    BindingFragment<FragmentProductListBinding>(R.layout.fragment_product_list) {
    private val viewModel by viewModels<ProductListViewModel> {
        ProductListViewModel.factory(DefaultShoppingRepository())
    }
    private lateinit var productAdapter: ProductAdapter

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }
        viewModel.loadProducts()
        initAppBar()
        initViews()
        initObservers()
    }

    private fun initAppBar() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.apply {
            title = getString(R.string.app_name)
            setDisplayHomeAsUpEnabled(false)
        }
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(
                    menu: Menu,
                    menuInflater: MenuInflater,
                ) {
                    menuInflater.inflate(R.menu.shopping_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    if (menuItem.itemId == R.id.menu_item_cart) {
                        parentFragmentManager.commit {
                            replace<ShoppingCartFragment>(
                                R.id.fragment_container_shopping,
                                ShoppingCartFragment.TAG,
                            )
                            addToBackStack(TAG)
                        }
                        return true
                    }
                    return false
                }
            },
            viewLifecycleOwner,
        )
    }

    private fun initViews() {
        binding?.apply {
            productAdapter =
                ProductAdapter(
                    onClickItem = { navigateToDetailView(it) },
                    onPlusItem = { viewModel.loadProducts() },
                )
            rvProductList.adapter = productAdapter
            rvProductList.layoutManager =
                GridLayoutManager(requireContext(), SPAN_COUNT).apply {
                    spanSizeLookup = spanSizeLookUp()
                }
            rvProductList.addItemDecoration(ProductItemDecoration(12.dp))
        }
    }

    private fun initObservers() {
        viewModel.products.observe(viewLifecycleOwner) {
            productAdapter.updateProducts(it)
        }
    }

    private fun navigateToDetailView(id: Long) {
        parentFragmentManager.commit {
            replace<ProductDetailFragment>(
                R.id.fragment_container_shopping,
                ProductDetailFragment.TAG,
                ProductDetailFragment.args(id),
            )
            addToBackStack(TAG)
        }
    }

    private fun spanSizeLookUp() =
        object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (productAdapter.getItemViewType(position) == ShoppingUiModel.ITEM_VIEW_TYPE_PLUS) {
                    ShoppingUiModel.PLUS_SPAN_COUNT
                } else {
                    ShoppingUiModel.PRODUCT_SPAN_COUNT
                }
            }
        }

    companion object {
        val TAG: String? = ProductListFragment::class.java.canonicalName
        private const val SPAN_COUNT = 2
    }
}
