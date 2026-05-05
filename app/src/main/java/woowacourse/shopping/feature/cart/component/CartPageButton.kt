package woowacourse.shopping.feature.cart.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.core.designsystem.theme.LightGreen

@Composable
fun CartPageButton(
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
    pageText: String,
    modifier: Modifier = Modifier,
    isPreviousEnabled: Boolean = true,
    isNextEnabled: Boolean = true,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NextPageButton(
            enabled = isPreviousEnabled,
            onClick = onPreviousClick,
            text = "<",
            contentDescription = "이전 페이지 이동 버튼",
            buttonShape =
                RoundedCornerShape(
                    topStart = 4.dp,
                    bottomStart = 4.dp,
                    topEnd = 0.dp,
                    bottomEnd = 0.dp,
                ),
        )
        Text(
            text = pageText,
            fontSize = 22.sp,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        NextPageButton(
            enabled = isNextEnabled,
            onClick = onNextClick,
            text = ">",
            contentDescription = "다음 페이지 이동 버튼",
            buttonShape =
                RoundedCornerShape(
                    topStart = 0.dp,
                    bottomStart = 0.dp,
                    topEnd = 4.dp,
                    bottomEnd = 4.dp,
                ),
        )
    }
}

@Composable
private fun NextPageButton(
    text: String,
    buttonShape: RoundedCornerShape,
    contentDescription: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    Box(
        modifier =
            modifier
                .clip(
                    shape = buttonShape,
                ).background(if (enabled) Color.LightGreen else Color.Gray)
                .clickable(
                    enabled = enabled,
                    onClick = onClick,
                ).padding(vertical = 12.dp, horizontal = 16.dp)
                .semantics {
                    this.contentDescription = contentDescription
                    role = Role.Button
               },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.W500,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CartPageButtonPreview() {
    CartPageButton(
        onPreviousClick = {},
        onNextClick = {},
        pageText = "1",
        isPreviousEnabled = false,
    )
}
