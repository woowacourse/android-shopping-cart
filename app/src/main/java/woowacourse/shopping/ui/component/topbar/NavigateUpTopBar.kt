package woowacourse.shopping.ui.component.topbar

import androidx.compose.foundation.clickable
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
fun NavigateUpTopBar(
    title: String,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ShoppingTopBar(type = TopBarType.TITLE, modifier = modifier) {
        Icon(
            painter = painterResource(R.drawable.ic_arrow_back),
            contentDescription = "뒤로가기 버튼",
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onNavigateUp),
            tint = Color.White,
        )

        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
            color = Color.White,
        )
    }
}

@Preview
@Composable
private fun NavigateUpTopBarPreview() {
    NavigateUpTopBar(
        title = "Cart",
        onNavigateUp = { },
    )
}
