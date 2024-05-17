package woowacourse.shopping.view.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import woowacourse.shopping.R
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl.Companion.PRODUCT_LOAD_PAGING_SIZE
import woowacourse.shopping.databinding.FragmentProductListBinding
import woowacourse.shopping.view.ViewModelFactory
import woowacourse.shopping.view.cart.ShoppingCartFragment
import woowacourse.shopping.view.detail.ProductDetailFragment
import woowacourse.shopping.view.products.adapter.ProductAdapter

class ProductsListFragment : Fragment(), OnClickProducts {
    private var _binding: FragmentProductListBinding? = null
    val binding: FragmentProductListBinding get() = _binding!!
    private val productListViewModel: ProductListViewModel by lazy {
        val viewModelFactory = ViewModelFactory { ProductListViewModel(ProductRepositoryImpl()) }
        viewModelFactory.create(ProductListViewModel::class.java)
    }
    private lateinit var adapter: ProductAdapter

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
        initView()
        observeData()
    }

    private fun initView() {
        loadPagingData()
        binding.onClickProduct = this
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
            when(productListState){
                ProductListState.Init -> {}
                ProductListState.LoadProductList.Success -> {}
                ProductListState.LoadProductList.Fail -> showMessage(MAX_PAGING_DATA)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun clickProductItem(productId: Long) {
        val productFragment =
            ProductDetailFragment().apply {
                arguments = ProductDetailFragment.createBundle(productId)
            }
        changeFragment(productFragment)
    }

    override fun clickShoppingCart() {
        val shoppingCartFragment = ShoppingCartFragment()
        changeFragment(shoppingCartFragment)
    }

    override fun clickLoadPagingData() {
        loadPagingData()
    }

    private fun changeFragment(nextFragment: Fragment) {
        parentFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, nextFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun loadPagingData() {
        productListViewModel.loadPagingProduct(PRODUCT_LOAD_PAGING_SIZE)
    }

    private fun showMessage(message: String) =
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()

    companion object {
        private const val MAX_PAGING_DATA = "모든 데이터가 로드 되었습니다."
    }
}
