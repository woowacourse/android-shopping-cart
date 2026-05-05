package woowacourse.shopping.ui.component.topbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    onCartClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ShoppingTopBar(type = TopBarType.LEADING, modifier = modifier) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
            color = Color.White,
        )

        Icon(
            painter = painterResource(R.drawable.ic_cart),
            contentDescription = "$title 화면 메뉴 버튼",
            tint = Color.White,
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onCartClick),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MainTopBarPreview() {
    MainTopBar(
        title = "안녕하세요 볼트입니다",
        onCartClick = { },
        modifier = Modifier.fillMaxWidth(),
    )
}
