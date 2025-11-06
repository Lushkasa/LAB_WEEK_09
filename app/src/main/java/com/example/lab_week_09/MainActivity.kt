package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Here, we use setContent instead of setContentView
        setContent {
            //Here, we wrap our content with the theme
            //You can check out the LAB_WEEK_09Theme inside Theme.kt
            LAB_WEEK_09Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    //We use Modifier.fillMaxSize() to make the surface fill the whole screen
                    modifier = Modifier.fillMaxSize(),
                    //We use MaterialTheme.colorScheme.background to get the background color
                    //and set it as the color of the surface
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home()
                }
            }
        }
    }
}

data class Student(
    var name: String
)

@Composable
fun Home() {
    val listData = remember{mutableStateListOf(
        Student("Tanu"),
        Student("Tina"),
        Student("Tono")
    )}
    //Here, we create a mutable state of Student
    //This is so that we can get the value of the input field
    var inputField = remember{mutableStateOf(Student("")) }
    //We call the HomeContent composable
    //Here, we pass:
    //listData to show the list of items inside HomeContent
    //inputField to show the input field value inside HomeContent
    //A lambda function to update the value of the inputField
    //A lambda function to add the inputField to the listData
    HomeContent(
        listData,
        inputField.value,
        { input -> inputField.value = inputField.value.copy(input) },
        {
            if (inputField.value.name.isNotBlank()) {
                listData.add(inputField.value)
                inputField.value = Student("")
            }
        }
    )
}

@Composable
fun HomeContent(
    listData: SnapshotStateList<Student>,
    inputField: Student,
    onInputValueChange: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    LazyColumn {
        item {
            Column(
            modifier = Modifier.padding(16.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Text(text = stringResource(
                id = R.string.enter_item)
            )
            //Here, we use TextField to display a text input field
            TextField(
                //Set the value of the input field
                value = inputField.name,
                //Set the keyboard type of the input field
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                        onValueChange = {
                    onInputValueChange(it)
                }
            )
            Button(onClick = {
                onButtonClick()
            }) {
                Text(text = stringResource(
                    id = R.string.button_click)
                )
            }
        }
        }
        items(listData) { item ->
            Column(
                modifier = Modifier.padding(vertical = 4.dp).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = item.name)
            }
        }
    }
}

//Here, we create a preview function of the Home composable
//This function is specifically used to show a preview of the Home composable
//This is only for development purpose
//@Preview(showBackground = true)
//@Composable
//fun PreviewHome() {
//    Home(listOf("Tanu", "Tina", "Tono"))
//}

