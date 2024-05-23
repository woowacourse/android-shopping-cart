package woowacourse.shopping.productList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.base.FragmentReplacer.replaceFragment
import woowacourse.shopping.cart.CartFragment
import woowacourse.shopping.databinding.FragmentProductListBinding
import woowacourse.shopping.listener.OnClickCartItemCounter
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.productDetail.ProductDetailFragment

class ProductListFragment : Fragment(), OnClickCartItemCounter {
    private val viewModel: ProductListViewModel by lazy { ProductListViewModel() }

    private val adapter: ProductRecyclerViewAdapter by lazy {
        ProductRecyclerViewAdapter(
            viewModel.loadedProducts.value ?: emptyList(),
            onClickCartItemCounter = this,
            cartItems = viewModel.cartItem.value?.let { mutableListOf(it) }.orEmpty(),
            onClick = { id -> navigateToProductDetail(id) },
        )
    }

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

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

                    if (totalItemCount == lastVisibleItem + 1) {
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

        viewModel.cartItem.observe(viewLifecycleOwner) {
            adapter.updateCartItem(it)
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
        replaceFragment(R.id.container, CartFragment(), parentFragmentManager)
    }

    private fun navigateToProductDetail(id: Int) {
        val productDetailFragment =
            ProductDetailFragment().apply {
                arguments = Bundle().apply { putInt("productId", id) }
            }

        replaceFragment(R.id.container, productDetailFragment, parentFragmentManager)
    }

    override fun increaseQuantity(cartItem: CartItem) {
        viewModel.increaseQuantity(cartItem)
    }

    override fun decreaseQuantity(cartItem: CartItem) {
        viewModel.decreaseQuantity(cartItem)
    }
}
