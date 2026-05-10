package woowacourse.shopping.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R

@Composable
fun AmountController(
    amount: String,
    onClickMinus: () -> Unit,
    onClickAdd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(42.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_minus),
            contentDescription = "상품 감소",
            modifier = Modifier
                .clickable(onClick = onClickMinus)
                .padding(10.dp),
            tint = Color(0xff555555),
        )
        Text(amount, fontSize = 22.sp, color = Color(0xff555555))
        Icon(
            painter = painterResource(R.drawable.ic_add),
            contentDescription = "상품 증가",
            modifier = Modifier
                .clickable(onClick = onClickAdd)
                .padding(10.dp),
            tint = Color(0xff555555),
        )
    }
}

@Preview
@Composable
private fun AmountControllerPreview() {
    AmountController(
        amount = "1",
        onClickMinus = { },
        onClickAdd = { },
    )
}
