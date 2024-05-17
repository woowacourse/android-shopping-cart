package woowacourse.shopping.view.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentProductListBinding
import woowacourse.shopping.view.MainActivity
import woowacourse.shopping.view.cart.ShoppingCartFragment
import woowacourse.shopping.view.detail.ProductDetailFragment
import woowacourse.shopping.view.products.adapter.ProductAdapter
import woowacourse.shopping.view.viewmodel.MainViewModel

class ProductsListFragment : Fragment(), OnClickProducts {
    private var _binding: FragmentProductListBinding? = null
    val binding: FragmentProductListBinding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
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
        mainViewModel = (requireActivity() as MainActivity).viewModel
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
        mainViewModel.products.observe(viewLifecycleOwner) { products ->
            adapter.updateProducts(addedProducts = products)
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
        runCatching {
            mainViewModel.loadPagingProduct(PRODUCT_LOAD_PAGING_SIZE)
        }.onFailure {
            showMaxItemMessage()
        }
    }

    private fun showMaxItemMessage() = Toast.makeText(this.context, R.string.max_paging_data_message, Toast.LENGTH_SHORT).show()

    companion object {
        private const val PRODUCT_LOAD_PAGING_SIZE = 20
    }
}
