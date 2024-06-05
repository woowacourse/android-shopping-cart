package woowacourse.shopping.ui.productList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentProductListBinding
import woowacourse.shopping.ui.FragmentNavigator
import woowacourse.shopping.ui.productList.adapter.ProductHistoryAdapter
import woowacourse.shopping.ui.productList.adapter.ProductListAdapter
import woowacourse.shopping.ui.productList.event.ProductListError
import woowacourse.shopping.ui.productList.event.ProductListEvent

class DefaultProductListFragment : Fragment() {
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("FragmentCartListBinding is not initialized")

    private val viewModel: ProductListViewModel by viewModels {
        DefaultProductListViewModel.factory()
    }

    private val productsAdapter: ProductListAdapter by lazy { ProductListAdapter(viewModel) }

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

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
        viewModel.uiState.isLastPage.observe(viewLifecycleOwner) { isLast ->
            when (isLast) {
                true -> binding.loadMoreButton.visibility = View.GONE
                false -> binding.loadMoreButton.visibility = View.VISIBLE
            }
        }
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
            productsAdapter.submitList(products)
        }
    }

    private fun observeProductHistory() {
        viewModel.uiState.productsHistory.observe(viewLifecycleOwner) {
            historyAdapter.submitList(it.reversed())
        }
    }

    private fun observeNavigationEvent() {
        viewModel.navigationEvent.observe(viewLifecycleOwner) { event ->
            when (event) {
                is ProductListEvent.NavigateToProductDetail ->
                    (requireActivity() as FragmentNavigator).navigateToProductDetail(event.productId)

                is ProductListEvent.NavigateToShoppingCart ->
                    (requireActivity() as FragmentNavigator).navigateToShoppingCart()
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

    private fun showToast(
        @StringRes stringId: Int,
    ) {
        Toast.makeText(
            requireContext(),
            stringId,
            Toast.LENGTH_SHORT,
        ).show()
    }

    companion object {
        const val TAG = "ProductListFragment"
    }
}
