package woowacourse.shopping.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.productlist.ProductListActivity
import woowacourse.shopping.productlist.ProductListActivity.ResultActivity.Companion.ACTIVITY_TYPE
import woowacourse.shopping.shoppingcart.uimodel.CartItemState
import woowacourse.shopping.shoppingcart.uimodel.CountChangeEvent
import woowacourse.shopping.shoppingcart.uimodel.ShoppingCartClickAction
import woowacourse.shopping.util.ViewModelFactory
import woowacourse.shopping.util.showToastMessage

class ShoppingCartActivity : AppCompatActivity(), ShoppingCartClickAction {
    private lateinit var binding: ActivityShoppingCartBinding
    private lateinit var adapter: ShoppingCartAdapter
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    private val viewModel: ShoppingCartViewModel by viewModels { ViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        attachAdapter()

        initShoppingCart()
        onBackPressedCallbackInit()

        updateView()
    }

    private fun attachAdapter() {
        adapter = ShoppingCartAdapter(this)
        binding.rcvShoppingCart.adapter = adapter
        binding.rcvShoppingCart.itemAnimator = null
    }

    private fun initShoppingCart() {
        viewModel.loadCartItems()
        viewModel.updatePageCount()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Cart"

        viewModel.cartItemState.observe(this) { state ->
            when (state) {
                is CartItemState.Init -> adapter.replaceItems(state.currentCartItems)
                is CartItemState.Loading -> showToastMessage(R.string.loading_message)
                is CartItemState.Error -> showToastMessage(R.string.network_error)
                is CartItemState.Change -> {}
            }
        }
    }

    private fun updateView() {
        viewModel.cartItemChange.observe(this) { event ->
            when (event) {
                is CountChangeEvent.AddNextPageOfItem -> adapter.addItem(event.result)
                is CountChangeEvent.ChangeItemCount -> adapter.changeProductInfo(event.result)
                is CountChangeEvent.DeleteCartItem -> adapter.deleteItemByProductId(event.result)
                is CountChangeEvent.MinusChangeFail -> showToastMessage(R.string.min_cart_item_message)
                is CountChangeEvent.PlusChangeFail -> showToastMessage(R.string.max_cart_item_message)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            setResultAndFinish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemRemoveBtnClicked(id: Long) {
        viewModel.deleteCartItem(id)
    }

    override fun onPlusCountClicked(id: Long) {
        viewModel.plusCartItemCount(id)
    }

    override fun onMinusCountClicked(id: Long) {
        viewModel.minusCartItemCount(id)
    }

    private fun onBackPressedCallbackInit() {
        onBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    setResultAndFinish()
                }
            }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun setResultAndFinish() {
        val intent =
            Intent(this, ProductListActivity::class.java).apply {
                putExtra(CHANGED_PRODUCT_ID, viewModel.changedProductIds.toLongArray())
                putExtra(ACTIVITY_TYPE, ProductListActivity.ResultActivity.CART.ordinal)
            }

        if (viewModel.changedProductIds.isEmpty()) {
            setResult(RESULT_CANCELED, intent)
            finish()
        } else {
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    companion object {
        const val CHANGED_PRODUCT_ID = "productId"

        fun newInstance(context: Context) = Intent(context, ShoppingCartActivity::class.java)
    }
}
