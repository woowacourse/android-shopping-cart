package woowacourse.shopping.productDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.FiveCartItemPagingStrategy
import woowacourse.shopping.ProductDetailViewModelFactory
import woowacourse.shopping.R
import woowacourse.shopping.TwentyItemsPagingStrategy
import woowacourse.shopping.databinding.FragmentProductDetailBinding
import woowacourse.shopping.repository.DummyShoppingCartItemRepository
import woowacourse.shopping.repository.DummyShoppingProductsRepository

class ProductDetailFragment : Fragment() {
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("FragmentCartListBinding is not initialized")

    private lateinit var factory: ProductDetailViewModelFactory
    private lateinit var viewModel: ProductDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater)

        arguments?.let {
            factory =
                ProductDetailViewModelFactory(
                    it.getInt("productId"),
                    DummyShoppingProductsRepository(TwentyItemsPagingStrategy()),
                    DummyShoppingCartItemRepository(FiveCartItemPagingStrategy()),
                )
            viewModel = ViewModelProvider(this, factory)[ProductDetailViewModel::class.java]
        }

        binding.vm = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.productDetailToolbar.setOnMenuItemClickListener {
            clickXButton(it)
        }
    }

    private fun clickXButton(it: MenuItem) =
        when (it.itemId) {
            R.id.action_x -> {
                parentFragmentManager.popBackStack()
                true
            }

            else -> false
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
