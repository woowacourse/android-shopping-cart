package woowacourse.shopping.productList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.shopping.databinding.FragmentProductListBinding
import woowacourse.shopping.repository.DummyProductStore

class ProductListFragment : Fragment() {

    private val adapter: ProductRecyclerViewAdapter by lazy { ProductRecyclerViewAdapter(DummyProductStore().loadData(1)) }
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
