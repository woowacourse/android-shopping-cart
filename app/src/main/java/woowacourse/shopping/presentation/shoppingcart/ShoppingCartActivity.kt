package woowacourse.shopping.presentation.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.domain.model.ShoppingCartItem
import woowacourse.shopping.presentation.BaseActivity
import woowacourse.shopping.presentation.util.QuantityClickListener

class ShoppingCartActivity : BaseActivity() {
    private val binding by bind<ActivityShoppingCartBinding>(R.layout.activity_shopping_cart)
    private val viewModel: ShoppingCartViewModel by viewModels { ShoppingCartViewModel.FACTORY }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpScreen(binding.root)
        setUpAppBar()
        setUpBinding()
        setUpAdapter()
    }

    private fun setUpAppBar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.label_shopping_cart_title)
        }
    }

    private fun setUpBinding() {
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@ShoppingCartActivity
        }
    }

    private fun setUpAdapter() {
        val adapter = ShoppingCartAdapter(
            quantityClickListener = object : QuantityClickListener {
                override fun increase(item: ShoppingCartItem) {
                    viewModel.increaseQuantity(item)
                }

                override fun decrease(item: ShoppingCartItem) {
                    viewModel.decreaseQuantity(item)
                }

            },
            clickListener = { item -> viewModel.deleteItem(item) }
        )
        binding.rvSelectedGoodsList.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@ShoppingCartActivity)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, ShoppingCartActivity::class.java)
    }
}
