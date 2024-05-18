package woowacourse.shopping.view.products

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import woowacourse.shopping.R
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.databinding.FragmentProductListBinding
import woowacourse.shopping.utils.ShoppingUtils
import woowacourse.shopping.view.FragmentChangeListener
import woowacourse.shopping.view.ViewModelFactory
import woowacourse.shopping.view.cart.ShoppingCartFragment
import woowacourse.shopping.view.detail.ProductDetailFragment
import woowacourse.shopping.view.products.adapter.ProductAdapter

class ProductsListFragment : Fragment(), OnClickProducts {
    private var fragmentChangeListener: FragmentChangeListener? = null
    private var _binding: FragmentProductListBinding? = null
    val binding: FragmentProductListBinding get() = _binding!!
    private val productListViewModel: ProductListViewModel by lazy {
        val viewModelFactory = ViewModelFactory { ProductListViewModel(ProductRepositoryImpl()) }
        viewModelFactory.create(ProductListViewModel::class.java)
    }
    private lateinit var adapter: ProductAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentChangeListener) {
            fragmentChangeListener = context
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    private fun initView() {
        loadPagingData()
        binding.onClickProduct = this
        binding.lifecycleOwner = this
        adapter =
            ProductAdapter(
                onClickProducts = this,
            ) { isLoadLastItem ->
                binding.isVisible = isLoadLastItem
            }
        binding.rvProducts.adapter = adapter
    }

    private fun observeData() {
        productListViewModel.products.observe(viewLifecycleOwner) { products ->
            adapter.updateProducts(addedProducts = products)
        }
        productListViewModel.productListState.observe(viewLifecycleOwner) { productListState ->
            when (productListState) {
                ProductListState.Init, ProductListState.LoadProductList.Success -> {}
                ProductListState.LoadProductList.Fail -> ShoppingUtils.makeToast(
                    requireContext(),
                    getString(R.string.max_paging_data)
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        fragmentChangeListener = null
    }

    override fun clickProductItem(productId: Long) {
        val productFragment =
            ProductDetailFragment().apply {
                arguments = ProductDetailFragment.createBundle(productId)
            }
        fragmentChangeListener?.changeFragment(productFragment)
    }

    override fun clickShoppingCart() {
        val shoppingCartFragment = ShoppingCartFragment()
        fragmentChangeListener?.changeFragment(shoppingCartFragment)
    }

    override fun clickLoadPagingData() {
        loadPagingData()
    }

    private fun loadPagingData() {
        productListViewModel.loadPagingProduct()
    }
}
