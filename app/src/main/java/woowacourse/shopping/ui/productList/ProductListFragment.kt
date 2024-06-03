package woowacourse.shopping.ui.productList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.UniversalViewModelFactory
import woowacourse.shopping.databinding.FragmentProductListBinding
import woowacourse.shopping.ui.cart.ShoppingCartFragment
import woowacourse.shopping.ui.productDetail.ProductDetailFragment
import woowacourse.shopping.ui.productList.adapter.ProductHistoryAdapter
import woowacourse.shopping.ui.productList.adapter.ProductListAdapter
import woowacourse.shopping.ui.productList.event.ProductListError
import woowacourse.shopping.ui.productList.event.ProductListNavigationEvent

class ProductListFragment : Fragment() {
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("FragmentCartListBinding is not initialized")

    private val factory: UniversalViewModelFactory = ProductListViewModel.factory()

    private val viewModel: ProductListViewModel by lazy {
        ViewModelProvider(this, factory)[ProductListViewModel::class.java]
    }

    private val productsAdapter: ProductListAdapter by lazy { ProductListAdapter(viewModel, viewModel) }
    private val historyAdapter: ProductHistoryAdapter by lazy { ProductHistoryAdapter(viewModel) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        binding.productDetailList.adapter = productsAdapter
        binding.productLatestList.adapter = historyAdapter

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadAll()
    }

    private fun hasMoreItems(
        totalItemCount: Int,
        lastVisibleItem: Int,
    ) = totalItemCount == lastVisibleItem + 1

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
        registerLoadMoreButtonListener()
    }

    private fun initObserve() {
        observeUiState()
        observeNavigationEvent()
        observeErrorEvent()
    }

    private fun observeUiState() {
        observeLoadedProducts()
        observeProductHistory()
    }

    private fun observeLoadedProducts() {
        viewModel.uiState.loadedProducts.observe(viewLifecycleOwner) { products ->
            productsAdapter.updateAllLoadedProducts(products)
        }
    }

    private fun observeProductHistory() {
        viewModel.uiState.productsHistory.observe(viewLifecycleOwner) {
            historyAdapter.update(it)
        }
    }

    private fun observeNavigationEvent() {
        viewModel.navigationEvent.observe(viewLifecycleOwner) { event ->
            when (event) {
                is ProductListNavigationEvent.ProductDetail -> navigateToProductDetail(event.productId)
                ProductListNavigationEvent.ShoppingCart -> navigateToShoppingCart()
            }
        }
    }

    private fun observeErrorEvent() {
        viewModel.errorEvent.observe(viewLifecycleOwner) {
            when (it) {
                ProductListError.FinalPage -> showToast(R.string.error_message_final_page)

                ProductListError.CartProductQuantity -> showToast(R.string.error_message_cart_product_quantity)

                ProductListError.LoadProductHistory -> showToast(R.string.error_message_product_history)

                ProductListError.LoadProducts -> showToast(R.string.error_message_load_products)

                ProductListError.AddProductInCart -> showToast(R.string.error_message_add_product_in_cart)

                ProductListError.UpdateProductQuantity -> showToast(R.string.error_message_update_products_quantity_in_cart)
            }
        }
    }

    private fun registerLoadMoreButtonListener() {
        binding.productDetailList.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(
                    recyclerView: RecyclerView,
                    dx: Int,
                    dy: Int,
                ) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                    if (viewModel.uiState.isLastPage.value == false && hasMoreItems(totalItemCount, lastVisibleItem)) {
                        binding.loadMoreButton.visibility = View.VISIBLE
                        return
                    }

                    binding.loadMoreButton.visibility = View.GONE
                }
            },
        )
    }

    private fun showToast(@StringRes stringId: Int) {
        Toast.makeText(
            requireContext(),
            stringId,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun navigateToShoppingCart() {
        navigateToFragment(ShoppingCartFragment())
    }

    private fun navigateToProductDetail(id: Long) = navigateToFragment(ProductDetailFragment.newInstance(id))

    private fun navigateToFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragment)
            addToBackStack(null)
            commit()
        }
    }

    companion object {
        const val TAG = "ProductListFragment"
    }
}
