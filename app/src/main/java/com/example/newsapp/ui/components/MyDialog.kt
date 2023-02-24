package com.example.newsapp.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.newsapp.R

@Composable
fun MyDialog(
    @StringRes title: Int,
    @StringRes positiveButtonText: Int = R.string.ok,
    @StringRes negativeButtonText: Int = R.string.cancel,
    dialogState: MutableState<Boolean>,
    onPositiveClicked : () -> Unit,
    onNegativeClicked : () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = { dialogState.value = false },
        content = {
            DialogContent(
                stringResource(id = title),
                stringResource(id = positiveButtonText),
                stringResource(id = negativeButtonText),
                dialogState,
                onPositiveClicked,
                onNegativeClicked,
                content
            )
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    )
}

@Composable
fun DialogContent(
    title: String,
    positiveButtonText: String,
    negativeButtonText: String,
    dialogState: MutableState<Boolean>,
    onPositiveClicked : () -> Unit,
    onNegativeClicked : () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxHeight(0.9f)
            .fillMaxWidth(1f),
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TitleAndButton(Modifier,title, dialogState)
            AddBody(Modifier.weight(1f,true),content)
            BottomButtons(Modifier.align(Alignment.End),
                positiveButtonText,negativeButtonText, dialogState = dialogState,onPositiveClicked,onNegativeClicked)
        }
    }
}

@Composable
private fun TitleAndButton(modifier: Modifier, title: String, dialogState: MutableState<Boolean>) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 24.sp)
        IconButton(
            modifier = Modifier.then(Modifier.size(24.dp)),
            onClick = {
                dialogState.value = false
            }) {
            Icon(
                Icons.Filled.Close,
                "contentDescription"
            )
        }
    }
    Divider(color = Color.DarkGray, thickness = 1.dp)
}

@Composable
private fun BottomButtons(
    modifier: Modifier, positiveButtonText: String,
    negativeButtonText: String,
    dialogState: MutableState<Boolean>,
    onPositiveClicked: () -> Unit,
    onNegativeClicked: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 2.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        TextButton(
            onClick = {
                onNegativeClicked.invoke()
                dialogState.value = false
            },
            modifier = Modifier
                .padding(end = 5.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = negativeButtonText)
        }
        TextButton(
            onClick = {
                onPositiveClicked.invoke()
                dialogState.value = false
            },
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = positiveButtonText)
        }

    }
}

@Composable
private fun AddBody(modifier: Modifier,content: @Composable () -> Unit) {
    Box(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        content()
    }
}
