package woowacourse.shopping.productList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentProductListBinding
import woowacourse.shopping.productDetail.ProductDetailFragment

class ProductListFragment : Fragment() {

    private val viewModel: ProductListViewModel by lazy { ProductListViewModel() }
    private val adapter: ProductRecyclerViewAdapter by lazy {
        ProductRecyclerViewAdapter(
            viewModel.loadProducts(),
            onClick = { id -> navigateToProductDetail(id) }
        )
    }
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater)
        binding.list.adapter = adapter
        return binding.root
    }

    private fun navigateToProductDetail(id: Int) {
        val productDetailFragment = ProductDetailFragment().apply {
            arguments = Bundle().apply {
                putInt("productId", id)
            }
        }

        parentFragmentManager.beginTransaction().apply {
            add(R.id.container, productDetailFragment)
            addToBackStack(null)
            commit()
        }
    }
}
