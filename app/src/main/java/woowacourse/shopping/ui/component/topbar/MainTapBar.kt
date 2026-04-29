package woowacourse.shopping.ui.component.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R

@Composable
fun MainTapBar(
    title: String,
    iconResources: Int,
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF555555))
            .padding(start = 26.dp, end = 20.dp, top = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
            color = Color.White,
        )

        Icon(
            painter = painterResource(iconResources),
            contentDescription = "$title 화면 메뉴 버튼",
            tint = Color.White,
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onIconClick),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MainTapBarPreview() {
    MainTapBar(
        title = "안녕하세요 볼트입니다",
        iconResources = R.drawable.ic_cart,
        onIconClick = { },
        modifier = Modifier.fillMaxWidth(),
    )
}
