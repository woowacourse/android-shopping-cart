package woowacourse.shopping.ui.cart.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.ui.ShoppingTypography

@Composable
fun CartPaging(
    currentPage: Int,
    totalPages: Int,
    modifier: Modifier = Modifier,
    onPreviousClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
) {
    val isPreviousEnabled = currentPage > 1
    val isNextEnabled = currentPage < totalPages

    Row(
        modifier =
            modifier
                .width(129.dp)
                .height(42.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        ArrowButton(
            isEnabled = isPreviousEnabled,
            roundedCornerShape = RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp),
            onClick = onPreviousClick,
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "이전",
                tint = Color.White,
            )
        }

        Text(
            text = "$currentPage",
            color = Color.Black,
            style = ShoppingTypography.titleMedium,
        )

        ArrowButton(
            isEnabled = isNextEnabled,
            roundedCornerShape = RoundedCornerShape(topEnd = 5.dp, bottomEnd = 5.dp),
            onClick = onNextClick,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "다음",
                tint = Color.White,
            )
        }
    }
}

@Composable
private fun ArrowButton(
    isEnabled: Boolean,
    roundedCornerShape: RoundedCornerShape,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .width(42.dp)
                .height(42.dp)
                .clip(roundedCornerShape)
                .background(if (isEnabled) Color(0xFF04C09E) else Color.Gray)
                .clickable(enabled = isEnabled, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun CartPagingPreview() {
    CartPaging(
        currentPage = 1,
        totalPages = 2,
    )
}

@Preview(showBackground = true, name = "활성화된 화살표 버튼")
@Composable
private fun ArrowButtonPreview1() {
    ArrowButton(
        isEnabled = true,
        roundedCornerShape = RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp),
        content = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "다음",
                tint = Color.White,
            )
        },
        onClick = {},
    )
}

@Preview(showBackground = true, name = "비활성화된 화살표 버튼")
@Composable
private fun ArrowButtonPreview2() {
    ArrowButton(
        isEnabled = false,
        roundedCornerShape = RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp),
        content = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "다음",
                tint = Color.White,
            )
        },
        onClick = {},
    )
}
