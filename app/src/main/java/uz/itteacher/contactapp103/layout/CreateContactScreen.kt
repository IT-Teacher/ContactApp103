package uz.itteacher.contactapp103.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateContactScreen(navController: NavHostController) {
    var name = remember { mutableStateOf("") }
    var surname = remember { mutableStateOf("") }
    var phone_num = remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize().padding(15.dp)) {
        Text("Ism", fontSize = 20.sp)

        TextField(
            value = name.value,
            onValueChange = {name.value = it},
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            placeholder = {Text("Ismni kiriting")}
        )
        Spacer(Modifier.height(20.dp))
        Text("Familiya", fontSize = 20.sp)

        TextField(
            value = surname.value,
            onValueChange = {surname.value = it},
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            placeholder = {Text("Familiyani kiriting")}
        )
        Spacer(Modifier.height(20.dp))
        Text("Telefon raqam", fontSize = 20.sp)

        TextField(
            value = phone_num.value,
            onValueChange = {phone_num.value = it},
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            placeholder = {Text("Telefon raqamini kiriting")},

        )
    }
}
