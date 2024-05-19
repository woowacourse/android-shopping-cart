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
import woowacourse.shopping.ProductListViewModelFactory
import woowacourse.shopping.R
import woowacourse.shopping.TwentyItemsPagingStrategy
import woowacourse.shopping.cart.CartFragment
import woowacourse.shopping.databinding.FragmentProductListBinding
import woowacourse.shopping.productDetail.ProductDetailFragment
import woowacourse.shopping.repository.DummyShoppingProductsRepository

class ProductListFragment : Fragment() {
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("FragmentCartListBinding is not initialized")

    private val factory = ProductListViewModelFactory(DummyShoppingProductsRepository(TwentyItemsPagingStrategy()))
    private val viewModel: ProductListViewModel by lazy {
        ViewModelProvider(this, factory)[ProductListViewModel::class.java]
    }

    private val adapter: ProductRecyclerViewAdapter by lazy {
        ProductRecyclerViewAdapter(
            viewModel.loadedProducts.value ?: emptyList(),
            onClick = { id -> navigateToProductDetail(id) },
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

                    if (totalItemCount == lastVisibleItem + 1 &&
                        viewModel.isLastPage.value == false
                    ) {
                        binding.loadMoreButton.visibility = View.VISIBLE
                    } else {
                        binding.loadMoreButton.visibility = View.GONE
                    }
                }
            },
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.productListToolbar.setNavigationOnClickListener {
            navigateToCart()
        }

        binding.productListToolbar.setOnMenuItemClickListener {
            clickCartButton(it)
        }
        viewModel.loadedProducts.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }
    }

    private fun clickCartButton(it: MenuItem) =
        when (it.itemId) {
            R.id.action_cart -> {
                navigateToCart()
                true
            }

            else -> false
        }

    private fun navigateToCart() {
        val cartFragment = CartFragment()

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.container, cartFragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun navigateToProductDetail(id: Int) {
        val productDetailFragment =
            ProductDetailFragment().apply {
                arguments =
                    Bundle().apply {
                        putInt("productId", id)
                    }
            }

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.container, productDetailFragment)
            addToBackStack(null)
            commit()
        }
    }
}
