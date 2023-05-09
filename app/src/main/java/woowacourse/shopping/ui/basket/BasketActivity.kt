package woowacourse.shopping.ui.basket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityBasketBinding

class BasketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBasketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_basket)
        binding.rvBasket.adapter = BasketAdapter { }
    }
}
