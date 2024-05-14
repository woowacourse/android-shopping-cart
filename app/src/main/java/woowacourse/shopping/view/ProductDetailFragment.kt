package woowacourse.shopping.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentProductDetailBinding
import woowacourse.shopping.databinding.FragmentProductListBinding

class ProductDetailFragment : Fragment() {
    private var _binding: FragmentProductDetailBinding? = null
    val binding: FragmentProductDetailBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
}
