package woowacourse.shopping.feature.goodsdetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.feature.GoodsUiModel

class GoodsDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_details)
    }

    companion object {
        private const val GOODS_KEY = "GOODS"

        fun newIntent(
            context: Context,
            goods: GoodsUiModel,
        ): Intent =
            Intent(context, GoodsDetailsActivity::class.java).apply {
                putExtra(GOODS_KEY, goods)
            }
    }
}
