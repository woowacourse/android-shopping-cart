package woowacourse.shopping.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.UniversalViewModelFactory
import woowacourse.shopping.data.source.DummyProductsDataSource
import woowacourse.shopping.data.source.DummyShoppingCartProductIdDataSource
import woowacourse.shopping.databinding.FragmentCartListBinding
import woowacourse.shopping.domain.repository.DefaultShoppingProductRepository

class ShoppingCartFragment : Fragment() {
    private var _binding: FragmentCartListBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("FragmentCartListBinding is not initialized")

    private val factory: UniversalViewModelFactory =
        UniversalViewModelFactory {
            ShoppingCartViewModel(
                DefaultShoppingProductRepository(
                    productsSource = DummyProductsDataSource(),
                    cartSource = DummyShoppingCartProductIdDataSource(),
                ),
            )
        }

    private val viewModel: ShoppingCartViewModel by lazy {
        ViewModelProvider(this, factory)[ShoppingCartViewModel::class.java]
    }

    private val adapter: CartItemRecyclerViewAdapter by lazy {
        CartItemRecyclerViewAdapter(onProductItemClickListener = viewModel, onItemQuantityChangeListener = viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCartListBinding.inflate(inflater)
        binding.cartList.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = this

        initNavigation()
        observeDeletedItem()
        observeItemsInCurrentPage()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadAll()
    }

    private fun initNavigation() {
        binding.productDetailToolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun observeDeletedItem() {
        viewModel.deletedItemId.observe(viewLifecycleOwner) { productId ->
            viewModel.deleteItem(productId)
        }
    }

    private fun observeItemsInCurrentPage() {
        viewModel.itemsInCurrentPage.observe(viewLifecycleOwner) { products ->
            Log.d(TAG, "observeItemsInCurrentPage: $products")
            adapter.updateData(products)
        }
    }

    companion object {
        private const val TAG = "ShoppingCartFragment"
    }
}
