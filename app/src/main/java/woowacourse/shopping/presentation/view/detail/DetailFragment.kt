package woowacourse.shopping.presentation.view.detail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentDetailBinding
import woowacourse.shopping.presentation.base.BaseFragment
import woowacourse.shopping.presentation.extension.getParcelableCompat
import woowacourse.shopping.presentation.model.ProductUiModel

class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val product = arguments.getParcelableCompat<ProductUiModel>(EXTRA_PRODUCT)
        binding.product = product
    }

    companion object {
        private const val EXTRA_PRODUCT = "product"

        fun newBundle(product: ProductUiModel) =
            bundleOf(
                EXTRA_PRODUCT to product,
            )
    }
}
