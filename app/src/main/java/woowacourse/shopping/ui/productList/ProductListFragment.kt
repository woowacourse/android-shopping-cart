package woowacourse.shopping.ui.productList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.UniversalViewModelFactory
import woowacourse.shopping.data.source.DummyProductsDataSource
import woowacourse.shopping.data.source.DummyShoppingCartProductIdDataSource
import woowacourse.shopping.databinding.FragmentProductListBinding
import woowacourse.shopping.domain.model.ProductCountEvent
import woowacourse.shopping.domain.repository.DefaultShoppingProductRepository
import woowacourse.shopping.ui.cart.ShoppingCartFragment
import woowacourse.shopping.ui.productDetail.ProductDetailFragment

class ProductListFragment : Fragment() {
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("FragmentCartListBinding is not initialized")

    private val factory: UniversalViewModelFactory =
        UniversalViewModelFactory {
            ProductListViewModel(
                productsRepository =
                    DefaultShoppingProductRepository(
                        productsSource = DummyProductsDataSource(),
                        cartSource = DummyShoppingCartProductIdDataSource(),
                    ),
            )
        }

    private val viewModel: ProductListViewModel by lazy {
        ViewModelProvider(this, factory)[ProductListViewModel::class.java]
    }

    private val adapter: ProductRecyclerViewAdapter by lazy { ProductRecyclerViewAdapter(viewModel, viewModel) }

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
        observeProductEvent()
    }

    private fun observeLoadedProducts() {
        viewModel.loadedProducts.observe(viewLifecycleOwner) { products ->
            adapter.updateAllLoadedProducts(products)
        }
    }

    private fun observeDetailProductDestination() {
        viewModel.productsEvent.observe(viewLifecycleOwner) { event ->
            when (event) {
                is ProductCountEvent.ProductCountAllCleared ->
                    Log.d(TAG, "observeDetailProductDestination: ProductCountAllCleared")

                is ProductCountEvent.ProductCountCleared ->
                    Log.d(TAG, "observeDetailProductDestination: ProductCountCleared")

                is ProductCountEvent.ProductCountCountChanged ->
                    Log.d(
                        TAG,
                        "observeDetailProductDestination: ProductCountChanged " +
                            "id: ${event.productId} count: ${event.count}",
                    )
            }
        }

        viewModel.detailProductDestinationId.observe(viewLifecycleOwner) { productId ->
            navigateToProductDetail(productId)
        }
    }

    private fun observeProductEvent() {
        viewModel.productsEvent.observe(viewLifecycleOwner) {
            adapter.updateProductInCart(it)
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

    companion object {
        private const val TAG = "ProductListFragment"
    }
}
