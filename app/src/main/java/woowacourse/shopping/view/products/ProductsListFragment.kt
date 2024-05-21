package woowacourse.shopping.view.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.databinding.FragmentProductListBinding
import woowacourse.shopping.view.cart.ShoppingCartFragment
import woowacourse.shopping.view.detail.ProductDetailFragment
import woowacourse.shopping.view.products.adapter.ProductAdapter
import woowacourse.shopping.view.products.adapter.ProductItemType.Companion.LOAD_MORE_VIEW_TYPE

class ProductsListFragment : Fragment() {
    private var _binding: FragmentProductListBinding? = null
    val binding: FragmentProductListBinding get() = _binding!!
    private lateinit var adapter: ProductAdapter

    private val productListViewModel: ProductListViewModel by lazy {
        val viewModelFactory = ProductListViewModelFactory(ProductRepositoryImpl(context = requireContext()))
        viewModelFactory.create(ProductListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setUpDataBinding()
        observeData()
    }

    private fun setUpDataBinding() {
        binding.productListActionHandler = productListViewModel
        adapter = ProductAdapter(productListActionHandler = productListViewModel)
        binding.rvProducts.adapter = adapter
        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (adapter.getItemViewType(position) == LOAD_MORE_VIEW_TYPE) return 2
                    return 1
                }
            }
        binding.rvProducts.layoutManager = layoutManager
    }

    private fun observeData() {
        productListViewModel.products.observe(viewLifecycleOwner) { products ->
            adapter.updateProducts(newProducts = products.items, products.hasNextPage)
        }

        productListViewModel.navigateToCart.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                navigateToShoppingCart()
            }
        }

        productListViewModel.navigateToDetail.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { productId ->
                navigateToDetail(productId)
            }
        }
    }

    private fun navigateToShoppingCart() {
        val shoppingCartFragment = ShoppingCartFragment()
        changeFragment(shoppingCartFragment)
    }

    private fun navigateToDetail(productId: Long) {
        val productFragment =
            ProductDetailFragment().apply {
                arguments = ProductDetailFragment.createBundle(productId)
            }
        changeFragment(productFragment)
    }

    private fun changeFragment(nextFragment: Fragment) {
        parentFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, nextFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showMaxItemMessage() = Toast.makeText(this.context, R.string.max_paging_data_message, Toast.LENGTH_SHORT).show()
}
