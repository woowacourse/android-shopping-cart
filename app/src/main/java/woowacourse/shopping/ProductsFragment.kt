package woowacourse.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.databinding.FragmentProductsBinding

class ProductsFragment : Fragment() {
    private lateinit var binding: FragmentProductsBinding
    private val productsAdapter: ProductsAdapter by lazy { ProductsAdapter(products) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvProducts.adapter = productsAdapter
        binding.rvProducts.layoutManager = GridLayoutManager(requireContext(), 2)

    }
}
