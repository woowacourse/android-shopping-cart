package woowacourse.shopping.presentation.goods.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.databinding.ActivityGoodsDetailBinding
import woowacourse.shopping.presentation.BaseActivity
import woowacourse.shopping.presentation.model.GoodsUiModel
import woowacourse.shopping.presentation.util.QuantitySelectorListener
import woowacourse.shopping.presentation.util.getSerializableCompat

class GoodsDetailActivity : BaseActivity() {
    private val binding by bind<ActivityGoodsDetailBinding>(R.layout.activity_goods_detail)
    private val viewModel: GoodsDetailViewModel by viewModels {
        GoodsDetailViewModel.provideFactory((application as ShoppingApplication).shoppingRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpScreen(binding.root)
        setUpBinding()

        viewModel.setGoods(intent.getSerializableCompat<GoodsUiModel>(EXTRA_GOODS))

        viewModel.onItemAddedToCart.observe(this) { count ->
            Toast.makeText(this, getString(R.string.text_save_goods, count), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpBinding() {
        binding.vm = viewModel
        binding.lifecycleOwner = this
        binding.selectorClickListener =
            object : QuantitySelectorListener {
                override fun onIncreaseQuantity(position: Int) {
                    viewModel.increaseCount()
                }

                override fun onDecreaseQuantity(position: Int) {
                    viewModel.decreaseCount()
                }
            }
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
            goods: GoodsUiModel,
        ): Intent =
            Intent(context, GoodsDetailActivity::class.java).apply {
                putExtra(EXTRA_GOODS, goods)
            }
    }
}
