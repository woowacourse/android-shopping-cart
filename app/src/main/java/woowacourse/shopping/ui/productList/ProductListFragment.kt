package woowacourse.shopping.ui.productList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.UniversalViewModelFactory
import woowacourse.shopping.databinding.FragmentProductListBinding
import woowacourse.shopping.ui.cart.ShoppingCartFragment
import woowacourse.shopping.ui.productDetail.ProductDetailFragment

class ProductListFragment : Fragment() {
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("FragmentCartListBinding is not initialized")

    private val factory: UniversalViewModelFactory = ProductListViewModel.factory()

    private val viewModel: ProductListViewModel by lazy {
        ViewModelProvider(this, factory)[ProductListViewModel::class.java]
    }

    private val productsAdapter: ProductRecyclerViewAdapter by lazy { ProductRecyclerViewAdapter(viewModel, viewModel) }
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

        showLoadMoreButton()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadAll()
    }

    private fun showLoadMoreButton() {
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

                    if (viewModel.isLastPage.value == false && hasMoreItems(totalItemCount, lastVisibleItem)) {
                        binding.loadMoreButton.visibility = View.VISIBLE
                        return
                    }

                    binding.loadMoreButton.visibility = View.GONE
                }
            },
        )
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

        observeNavigationShoppingCart()
        observeLoadedProducts()
        observeDetailProductDestination()
        viewModel.productsHistory.observe(viewLifecycleOwner) {
            historyAdapter.update(it)
        }
    }

    private fun observeNavigationShoppingCart() {
        viewModel.shoppingCartDestination.observe(viewLifecycleOwner) {
            navigateToShoppingCart()
        }
    }

    private fun observeLoadedProducts() {
        viewModel.loadedProducts.observe(viewLifecycleOwner) { products ->
            productsAdapter.updateAllLoadedProducts(products)
        }
    }

    private fun observeDetailProductDestination() {
        viewModel.detailProductDestinationId.observe(viewLifecycleOwner) { productId ->
            navigateToProductDetail(productId)
        }
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
