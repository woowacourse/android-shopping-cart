package woowacourse.shopping.ui.shopping.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.R

@Composable
fun SelectItemCountBox(
    count: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White)
                .size(126.dp, 42.dp)
                .padding(horizontal = 15.dp, vertical = 11.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .size(13.dp, 19.dp)
                    .clickable { onDecrease() },
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.minus_icon),
                contentDescription = "감소 버튼",
            )
        }
        Text(
            text = count.toString(),
            color = Color.Black,
        )
        Box(
            modifier =
                Modifier
                    .size(13.dp, 19.dp)
                    .clickable { onIncrease() },
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.plus_icon),
                contentDescription = "증가 버튼",
            )
        }
    }
}

@Preview
@Composable
private fun SelectItemCountBoxPreview() {
    SelectItemCountBox(
        count = 0,
        onIncrease = {},
        onDecrease = {},
    )
}
