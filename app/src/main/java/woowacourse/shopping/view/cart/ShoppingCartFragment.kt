package woowacourse.shopping.view.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import woowacourse.shopping.R
import woowacourse.shopping.data.repository.ShoppingCartRepositoryImpl
import woowacourse.shopping.data.repository.ShoppingCartRepositoryImpl.Companion.CART_ITEM_LOAD_PAGING_SIZE
import woowacourse.shopping.data.repository.ShoppingCartRepositoryImpl.Companion.CART_ITEM_PAGE_SIZE
import woowacourse.shopping.data.repository.ShoppingCartRepositoryImpl.Companion.MIN_PAGE_COUNT
import woowacourse.shopping.databinding.FragmentShoppingCartBinding
import woowacourse.shopping.view.ViewModelFactory
import woowacourse.shopping.view.cart.adapter.ShoppingCartAdapter
import woowacourse.shopping.view.detail.ProductDetailFragment

class ShoppingCartFragment : Fragment(), OnClickShoppingCart {
    private var _binding: FragmentShoppingCartBinding? = null
    val binding: FragmentShoppingCartBinding get() = _binding!!

    private val shoppingCartViewModel: ShoppingCartViewModel by lazy {
        val viewModelFactory =
            ViewModelFactory { ShoppingCartViewModel(ShoppingCartRepositoryImpl(context = requireContext())) }
        viewModelFactory.create(ShoppingCartViewModel::class.java)
    }
    private lateinit var adapter: ShoppingCartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentShoppingCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    private fun initView() {
        shoppingCartViewModel.loadPagingCartItemList(CART_ITEM_LOAD_PAGING_SIZE)
        binding.onClickShoppingCart = this
        binding.currentPage = shoppingCartViewModel.currentPage
        adapter =
            ShoppingCartAdapter(
                onClickShoppingCart = this,
                loadLastItem = {
                    shoppingCartViewModel.loadPagingCartItemList(CART_ITEM_LOAD_PAGING_SIZE)
                },
            )
        binding.rvShoppingCart.adapter = adapter
    }

    private fun observeData() {
        shoppingCartViewModel.shoppingCart.cartItems.observe(viewLifecycleOwner) {
            updateRecyclerView()
        }
        shoppingCartViewModel.shoppingCartState.observe(viewLifecycleOwner) { shoppingCartState ->
            when (shoppingCartState) {
                ShoppingCartState.DeleteShoppingCart.Success,
                ShoppingCartState.LoadCartItemList.Success,
                ShoppingCartState.Init,
                -> {
                }

                ShoppingCartState.DeleteShoppingCart.Fail ->
                    showMessage(
                        requireContext().getString(
                            R.string.error_delete_data,
                        ),
                    )

                ShoppingCartState.LoadCartItemList.Fail ->
                    showMessage(
                        requireContext().getString(R.string.max_paging_data),
                    )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun clickBack() {
        parentFragmentManager.popBackStack()
    }

    override fun clickCartItem(productId: Long) {
        val productFragment =
            ProductDetailFragment().apply {
                arguments = ProductDetailFragment.createBundle(productId)
            }
        changeFragment(productFragment)
    }

    override fun clickRemoveCartItem(cartItemId: Long) {
        shoppingCartViewModel.deleteShoppingCartItem(cartItemId)
    }

    override fun clickPrevPage() {
        if (isExistPrevPage()) {
            binding.currentPage = --shoppingCartViewModel.currentPage
            updateRecyclerView()
        }
    }

    override fun clickNextPage() {
        if (isExistNextPage()) {
            binding.currentPage = ++shoppingCartViewModel.currentPage
            updateRecyclerView()
        } else {
            showMessage(requireContext().getString(R.string.max_paging_data))
        }
    }

    private fun changeFragment(nextFragment: Fragment) {
        parentFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, nextFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun updateRecyclerView() {
        val startIndex = (shoppingCartViewModel.currentPage - MIN_PAGE_COUNT) * CART_ITEM_PAGE_SIZE
        val endIndex =
            minOf(
                shoppingCartViewModel.currentPage * CART_ITEM_PAGE_SIZE,
                shoppingCartViewModel.totalItemSize,
            )
        val newData =
            shoppingCartViewModel.shoppingCart.cartItems.value?.subList(startIndex, endIndex)
                ?: emptyList()
        adapter.updateCartItems(hasLastItem(endIndex), newData)
        updateImageButtonColor()
    }

    private fun hasLastItem(endIndex: Int): Boolean {
        return endIndex >= (shoppingCartViewModel.totalItemSize)
    }

    private fun isExistPrevPage(): Boolean {
        return shoppingCartViewModel.currentPage > MIN_PAGE_COUNT
    }

    private fun isExistNextPage(): Boolean {
        return shoppingCartViewModel.currentPage * CART_ITEM_PAGE_SIZE < shoppingCartViewModel.totalItemSize
    }

    private fun updateImageButtonColor() {
        binding.onPrevButton = isExistPrevPage()
        binding.onNextButton = isExistNextPage()
    }

    private fun showMessage(message: String) = Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
}
