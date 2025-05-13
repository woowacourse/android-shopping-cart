package woowacourse.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.FragmentDetailProductBinding

class ProductDetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailProductBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDetailProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        Glide
            .with(this)
            .load(products[0].imageUrl)
            .placeholder(R.drawable.maxim_arabica)
            .fallback(R.drawable.maxim_arabica)
            .error(R.drawable.maxim_arabica)
            .into(binding.ivProductDetail)
        binding.tvProductDetailName.text = products[0].name
        binding.tvProductDetailPrice.text = "99,800원"
    }
}
