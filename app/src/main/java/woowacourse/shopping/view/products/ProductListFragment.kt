package woowacourse.shopping.view.products

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import woowacourse.shopping.databinding.FragmentProductListBinding
import woowacourse.shopping.view.MainActivity
import woowacourse.shopping.view.MainViewModel
import woowacourse.shopping.view.products.adapter.ProductAdapter

class ProductListFragment : Fragment(),OnClickProduct {
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
        adapter = ProductAdapter(onClickProduct = this)

        binding.rvProducts.adapter = adapter
        mainViewModel.products.observe(viewLifecycleOwner) { products ->
            adapter.updateProducts(products = products)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
