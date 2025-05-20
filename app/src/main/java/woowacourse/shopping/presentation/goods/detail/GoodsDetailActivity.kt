package woowacourse.shopping.presentation.goods.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityGoodsDetailBinding
import woowacourse.shopping.presentation.BaseActivity
import woowacourse.shopping.presentation.model.GoodsUiModel
import woowacourse.shopping.presentation.util.getParcelableCompat

class GoodsDetailActivity : BaseActivity() {
    private val binding by bind<ActivityGoodsDetailBinding>(R.layout.activity_goods_detail)
    private val viewModel: GoodsDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpScreen(binding.root)
        setUpBinding()
        viewModel.setGoods(intent.getParcelableCompat<GoodsUiModel>(EXTRA_GOODS))
    }

    private fun setUpBinding() {
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@GoodsDetailActivity
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
            goodsUiModel: GoodsUiModel,
        ): Intent =
            Intent(context, GoodsDetailActivity::class.java).apply {
                putExtra(EXTRA_GOODS, goodsUiModel)
            }
    }
}
