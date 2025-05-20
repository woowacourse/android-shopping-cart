package woowacourse.shopping.presentation.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.presentation.BaseActivity

class ShoppingCartActivity : BaseActivity() {
    private val binding by bind<ActivityShoppingCartBinding>(R.layout.activity_shopping_cart)
    private val viewModel: ShoppingCartViewModel by viewModels {
        ShoppingCartViewModel.provideFactory((application as ShoppingApplication).shoppingRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpScreen(binding.root)
        setupAppBar()

        binding.vm = viewModel
        binding.lifecycleOwner = this

        val adapter = ShoppingCartAdapter { goods -> viewModel.deleteGoods(goods) }
        binding.rvSelectedGoodsList.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@ShoppingCartActivity)
        }

        viewModel.goods.observe(this) { goods ->
            adapter.updateItems(goods)
        }
    }

    private fun setupAppBar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.label_shopping_cart_title)
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
