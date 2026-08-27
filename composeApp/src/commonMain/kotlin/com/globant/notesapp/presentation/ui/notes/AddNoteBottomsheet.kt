package com.globant.notesapp.presentation.ui.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.globant.notesapp.theme.NoteAppTheme
import io.ktor.http.ContentType
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(bottomsheetState: SheetState, onDismiss: () -> Unit) {
    val noteListViewModel = koinViewModel<NoteListViewModel>()
    ModalBottomSheet(modifier = Modifier.wrapContentSize().background(Color.Transparent), sheetState = bottomsheetState, sheetGesturesEnabled = false, onDismissRequest = onDismiss) {
        AddNoteContent(
            onSaveNote = { title, content ->
                noteListViewModel.upsertNote(
                    Note(title = title, content = content)
                )
            },
            onDismiss = onDismiss
        )
    }
}

@Composable
fun AddNoteContent(
    onSaveNote: (String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var isTitleError by remember { mutableStateOf<Boolean>(false) }
    var isNoteContentError by remember { mutableStateOf<Boolean>(false) }
    val noteTitle = remember { mutableStateOf("") }
    val noteContent = remember { mutableStateOf("") }

    val currentVehicle = LocalVehicle.current
    Column(
        modifier = Modifier.padding(10.dp).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
            text = currentVehicle.name,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        CustomTextField(
            value = noteTitle.value,
            onValueChange = {
                noteTitle.value = it
                if (noteTitle.value.isNotEmpty()) isTitleError = false
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth(),
            isError = isTitleError,
            label = "Enter title",
            leadingIcon = Icons.Outlined.Create,
        )
        Spacer(modifier = Modifier.size(20.dp))
        CustomTextField(
            value = noteContent.value,
            onValueChange = {
                noteContent.value = it
                if (noteContent.value.isNotEmpty()) isNoteContentError = false
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            isError = isNoteContentError,
            modifier = Modifier.fillMaxWidth(),
            minLines = 4,
            maxLines = 6,
            label = "Enter Content",
            leadingIcon = Icons.Outlined.EditNote
        )
        Spacer(modifier = Modifier.size(20.dp))
        Button(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            onClick = {
                if (noteTitle.value.isEmpty()) {
                    isTitleError = true
                    return@Button
                }
                if (noteContent.value.isEmpty()) {
                    isNoteContentError = true
                    return@Button
                }
                onSaveNote(noteTitle.value, noteContent.value)
                onDismiss.invoke()
            }) {
            Text(
                modifier = Modifier.padding(4.dp),
                text = "Save Note",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    modifier: Modifier,
    minLines: Int = 1,
    singleLine: Boolean = false,
    textStyle: TextStyle,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    isError: Boolean = false,
    leadingIcon: ImageVector? = null
) {
    BasicTextField(
        cursorBrush = if (isError) SolidColor(Color.Red) else SolidColor(MaterialTheme.colorScheme.primary),
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.border(
            1.dp, if (isError) Color.Red else Color.Gray, RoundedCornerShape(4.dp)
        ).padding(12.dp),
        minLines = minLines,
        singleLine = singleLine,
        maxLines = if (singleLine) 1 else Int.MAX_VALUE,
        textStyle = textStyle,

        decorationBox = { innerTextField ->
            leadingIcon?.let {
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        modifier = Modifier.padding(top = 2.dp, end = 8.dp)
                    )
                    Box {
                        if (value.isEmpty()) {
                            Text(label, color = Color.Gray)
                        }
                        innerTextField()
                    }
                }
            }
        })
}

@Composable
@Preview(showBackground = true)
fun AddNoteContentPreview() {
    NoteAppTheme {
        AddNoteContent(
            onSaveNote = { _, _ -> },
            onDismiss = {}
        )
    }
}