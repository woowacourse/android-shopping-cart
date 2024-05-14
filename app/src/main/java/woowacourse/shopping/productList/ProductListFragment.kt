package woowacourse.shopping.productList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.shopping.databinding.FragmentProductListBinding

class ProductListFragment : Fragment() {

    private val viewModel: ProductListViewModel by lazy { ProductListViewModel() }
    private val adapter: ProductRecyclerViewAdapter by lazy { ProductRecyclerViewAdapter(viewModel.loadProducts()) }
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

}
