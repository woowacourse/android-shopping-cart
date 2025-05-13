package woowacourse.shopping.presentation.goods

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityGoodsBinding
import woowacourse.shopping.util.DummyData.GOODS

class GoodsActivity : AppCompatActivity() {
    private val binding: ActivityGoodsBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_goods)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val goodsData = GOODS

        binding.rvGoodsList.adapter = GoodsAdapter(goodsData)
        binding.rvGoodsList.layoutManager = GridLayoutManager(this, 2)
    }
}
