package woowacourse.shopping.view.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.data.cartItem.CartItemDatabase
import woowacourse.shopping.data.cartItem.CartItemLocalDataSource
import woowacourse.shopping.data.cartItem.CartRepositoryImpl
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.data.recentvieweditem.RecentViewedItemDatabase
import woowacourse.shopping.data.recentvieweditem.RecentViewedItemRepositoryImpl
import woowacourse.shopping.data.recentvieweditem.RecentViewedLocalDataSource
import woowacourse.shopping.databinding.FragmentProductListBinding
import woowacourse.shopping.view.MainViewModel
import woowacourse.shopping.view.cart.ShoppingCartFragment
import woowacourse.shopping.view.detail.ProductDetailFragment
import woowacourse.shopping.view.products.adapter.ProductAdapter
import woowacourse.shopping.view.products.adapter.ProductItemType.Companion.LOAD_MORE_VIEW_TYPE
import woowacourse.shopping.view.products.recentproduct.RecentProductAdapter

class ProductsListFragment : Fragment() {
    private var _binding: FragmentProductListBinding? = null
    val binding: FragmentProductListBinding get() = _binding!!

    private val sharedViewModel: MainViewModel by activityViewModels()
    private val productListViewModel: ProductListViewModel by lazy {
        val viewModelFactory =
            ProductListViewModelFactory(
                ProductRepositoryImpl(),
                CartRepositoryImpl(CartItemLocalDataSource(CartItemDatabase.getInstance(requireContext()))),
                RecentViewedItemRepositoryImpl(RecentViewedLocalDataSource(RecentViewedItemDatabase.getInstance(requireContext()))),
            )
        viewModelFactory.create(ProductListViewModel::class.java)
    }

    private lateinit var productAdapter: ProductAdapter
    private lateinit var recentProductAdapter: RecentProductAdapter

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
        setUpProductList()
        setUPRecentViewedList()
        observeData()
        observeSharedData()
    }

    private fun setUpDataBinding() {
        binding.viewModel = productListViewModel
        binding.productListActionHandler = productListViewModel
    }

    private fun setUpProductList() {
        productAdapter =
            ProductAdapter(
                productListActionHandler = productListViewModel,
                countActionHandler = productListViewModel,
            )
        binding.rvProducts.adapter = productAdapter
        val layoutManager =
            GridLayoutManager(context, 2).apply {
                spanSizeLookup =
                    object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            if (productAdapter.getItemViewType(position) == LOAD_MORE_VIEW_TYPE) return 2
                            return 1
                        }
                    }
            }
        binding.rvProducts.layoutManager = layoutManager
    }

    private fun setUPRecentViewedList() {
        recentProductAdapter =
            RecentProductAdapter(
                productListActionHandler = productListViewModel,
            )
        binding.rvRecentProducts.adapter = recentProductAdapter
    }

    private fun observeData() {
        productListViewModel.products.observe(viewLifecycleOwner) { products ->
            productAdapter.updateProducts(products.items, products.hasNextPage)
        }

        productListViewModel.updateProductCount.observe(viewLifecycleOwner) { updatedInfo ->
            productAdapter.updateProductQuantity(updatedInfo.productId, updatedInfo.updatedQuantity)
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

        productListViewModel.recentViewed.observe(viewLifecycleOwner) {
            recentProductAdapter.updateRecentProducts(it)
        }
    }

    private fun observeSharedData() {
        sharedViewModel.updateProductEvent.observe(viewLifecycleOwner) { updatedInfo ->
            updatedInfo.getContentIfNotHandled()?.let {
                productAdapter.updateProductQuantity(it.productId, it.updatedQuantity)
                productListViewModel.updateTotalCount()
            }
        }

        sharedViewModel.updatedRecentProduct.observe(viewLifecycleOwner) { updatedRecentViewed ->
            updatedRecentViewed.getContentIfNotHandled()?.let {
                productListViewModel.updateRecentViewedItems()
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
            .addToBackStack("detailFragment")
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
