package woowacourse.shopping.view.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentProductListBinding
import woowacourse.shopping.view.MainActivity
import woowacourse.shopping.view.MainViewModel
import woowacourse.shopping.view.cart.ShoppingCartFragment
import woowacourse.shopping.view.detail.ProductDetailFragment
import woowacourse.shopping.view.products.adapter.ProductAdapter

class ProductsListFragment : Fragment(), OnClickProducts {
    private var _binding: FragmentProductListBinding? = null
    val binding: FragmentProductListBinding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = (requireActivity() as MainActivity).viewModel
        initView()
    }

    private fun initView(){
        binding.onClickProduct = this
        adapter = ProductAdapter(onClickProducts = this)

        binding.rvProducts.adapter = adapter
        mainViewModel.products.observe(viewLifecycleOwner) { products ->
            adapter.updateProducts(products = products)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun clickProductItem(productId: Long) {
        val productFragment = ProductDetailFragment().apply {
            arguments = ProductDetailFragment.createBundle(productId)
        }
        changeFragment(productFragment)
    }

    override fun clickShoppingCart() {
        val shoppingCartFragment = ShoppingCartFragment()
        changeFragment(shoppingCartFragment)
    }

    private fun changeFragment(nextFragment: Fragment){
        parentFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, nextFragment)
            .addToBackStack(null)
            .commit()
    }


}
