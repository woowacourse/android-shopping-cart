package woowacourse.shopping.productList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.NumberPagingStrategy
import woowacourse.shopping.R
import woowacourse.shopping.UniversalViewModelFactory
import woowacourse.shopping.cart.ShoppingCartFragment
import woowacourse.shopping.databinding.FragmentProductListBinding
import woowacourse.shopping.productDetail.ProductDetailFragment
import woowacourse.shopping.repository.DummyShoppingProductsRepository

class ProductListFragment : Fragment() {
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("FragmentCartListBinding is not initialized")

    private val factory: UniversalViewModelFactory =
        UniversalViewModelFactory {
            ProductListViewModel(
                DummyShoppingProductsRepository(
                    NumberPagingStrategy(countPerLoad = 20),
                ),
            )
        }

    private val viewModel: ProductListViewModel by lazy {
        ViewModelProvider(this, factory)[ProductListViewModel::class.java]
    }

    private val adapter: ProductRecyclerViewAdapter by lazy {
        ProductRecyclerViewAdapter(
            viewModel.loadedProducts.value ?: emptyList(),
            onProductItemClickListener = viewModel,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater)
        binding.productDetailList.adapter = adapter
        binding.vm = viewModel

        showLoadMoreButton()
        return binding.root
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

        binding.productListToolbar.setOnMenuItemClickListener(::navigateToMenuItem)

        observeLoadedProducts()
        observeDetailProductDestination()
    }

    private fun observeLoadedProducts() {
        viewModel.loadedProducts.observe(viewLifecycleOwner) { products ->
            adapter.updateData(products)
        }
    }

    private fun observeDetailProductDestination() {
        viewModel.detailProductDestinationId.observe(viewLifecycleOwner) { productId ->
            navigateToProductDetail(productId)
        }
    }

    private fun navigateToMenuItem(it: MenuItem) =
        when (it.itemId) {
            R.id.action_cart -> navigateToShoppingCart()

            else -> false
        }

    private fun navigateToShoppingCart(): Boolean {
        navigateToFragment(ShoppingCartFragment())
        return true
    }

    private fun navigateToProductDetail(id: Int) = navigateToFragment(ProductDetailFragment.newInstance(id))

    private fun navigateToFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragment)
            addToBackStack(null)
            commit()
        }
    }
}
