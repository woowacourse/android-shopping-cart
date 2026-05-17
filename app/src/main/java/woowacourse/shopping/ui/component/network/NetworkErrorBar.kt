package woowacourse.shopping.ui.component.network

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NetworkErrorBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray),
    ) {
        Text(
            text = "네트워크 연결이 끊겼습니다.",
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 2.dp),
        )
    }
}

@Preview
@Composable
private fun NetworkErrorBarPreview() {
    NetworkErrorBar()
}
