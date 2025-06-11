package woowacourse.shopping.presentation.goods.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityGoodsDetailBinding
import woowacourse.shopping.domain.model.shoppingcart.ShoppingCartItem
import woowacourse.shopping.presentation.BaseActivity
import woowacourse.shopping.presentation.util.QuantityClickListener
import woowacourse.shopping.presentation.util.ShoppingCartEvent

class GoodsDetailActivity : BaseActivity() {
    private val binding by bind<ActivityGoodsDetailBinding>(R.layout.activity_goods_detail)
    private val viewModel: GoodsDetailViewModel by viewModels {
        GoodsDetailViewModel.FACTORY
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpScreen(binding.root)
        setUpBinding()
        setUpClickListener()
        observeEvent()
        viewModel.setGoods(intent.getLongExtra(EXTRA_GOODS, 0L))
    }

    private fun setUpBinding() {
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@GoodsDetailActivity
        }
    }

    private fun setUpClickListener() {
        binding.clickListener =
            object : QuantityClickListener {
                override fun increase(item: ShoppingCartItem) {
                    viewModel.increaseQuantity()
                }

                override fun decrease(item: ShoppingCartItem) {
                    viewModel.decreaseQuantity()
                }
            }
    }

    private fun observeEvent() {
        viewModel.shoppingCartEvent.observe(this) { event ->
            when (event) {
                ShoppingCartEvent.SUCCESS -> {
                    showToast(R.string.text_save_success)
                    finish()
                }
                ShoppingCartEvent.FAILURE -> {
                    showToast(R.string.text_save_failure)
                }
            }
        }
    }

    private fun showToast(stringRes: Int) {
        Toast.makeText(this, getString(stringRes), Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.goods_detail_action_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cancel -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val EXTRA_GOODS = "goods"

        fun newIntent(
            context: Context,
            id: Long,
        ): Intent =
            Intent(context, GoodsDetailActivity::class.java).apply {
                putExtra(EXTRA_GOODS, id)
            }
    }
}
