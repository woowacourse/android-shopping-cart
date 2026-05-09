package woowacourse.shopping.ui.component.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R

@Composable
fun MainTopBar(
    title: String,
    cartProductCount: Int,
    onCartClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ShoppingTopBar(
        paddingValues = PaddingValues(start = 26.dp, end = 20.dp, top = 16.dp, bottom = 16.dp),
        modifier = modifier,
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
            color = Color.White,
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .clickable(onClick = onCartClick),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_cart),
                contentDescription = "장바구니 버튼",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp),
            )

            if (cartProductCount > 0) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF04C09E)),
                ) {
                    Text(
                        text = cartProductCount.toString(),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier.align(alignment = Alignment.Center),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainTopBarPreview() {
    MainTopBar(
        title = "안녕하세요 볼트입니다",
        onCartClick = { },
        cartProductCount = 2,
        modifier = Modifier.fillMaxWidth(),
    )
}
