package woowacourse.shopping.view.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import woowacourse.shopping.databinding.FragmentProductDetailBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.utils.NoSuchDataException
import woowacourse.shopping.view.MainActivity
import woowacourse.shopping.view.MainViewModel

class ProductDetailFragment : Fragment(),OnClickDetail {
    private var _binding: FragmentProductDetailBinding? = null
    val binding: FragmentProductDetailBinding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = (requireActivity() as MainActivity).viewModel
        val product = mainViewModel.loadProductItem(receiveId())
        initView(product)
    }

    private fun receiveId(): Long{
        return arguments?.getLong(PRODUCT_ID) ?: throw NoSuchDataException()
    }

    private fun initView(product: Product){
        binding.product =  product
        binding.onClickDetail = this
        Glide.with(requireActivity())
            .load(product.imageUrl)
            .override(Target.SIZE_ORIGINAL)
            .into(binding.ivDetail)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun clickClose() {
        parentFragmentManager.popBackStack()
    }

    override fun clickAddCart(product: Product) {
        mainViewModel.addShoppingCartItem(product)
    }

    companion object {
        fun createBundle(id: Long): Bundle {
            return Bundle().apply { putLong(PRODUCT_ID, id) }
        }

        private const val PRODUCT_ID = "productId"
    }
}
