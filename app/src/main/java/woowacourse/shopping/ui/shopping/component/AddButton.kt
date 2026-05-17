package woowacourse.shopping.ui.shopping.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AddButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        shape = CircleShape,
        modifier =
            modifier
                .size(48.dp)
                .background(
                    color = Color.White,
                    shape = CircleShape,
                ),
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "추가 아이콘",
            tint = Color.DarkGray,
            modifier = Modifier.size(40.dp),
        )
    }
}

@Preview
@Composable
private fun AddButtonPreview() {
    AddButton(
        onClick = {},
    )
}
