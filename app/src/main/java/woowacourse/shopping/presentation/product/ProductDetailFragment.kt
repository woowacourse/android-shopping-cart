package woowacourse.shopping.presentation.product

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentProductDetailBinding
import woowacourse.shopping.presentation.base.BindingFragment

class ProductDetailFragment :
    BindingFragment<FragmentProductDetailBinding>(R.layout.fragment_product_detail) {
    private val viewModel: ProductDetailViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding?.also {
            it.lifecycleOwner = viewLifecycleOwner
            it.vm = viewModel
        }
    }

    companion object {
        val TAG: String? = ProductDetailFragment::class.java.canonicalName
    }
}
