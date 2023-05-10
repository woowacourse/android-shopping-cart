package woowacourse.shopping.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        setSupportActionBar(findViewById(R.id.cart_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
