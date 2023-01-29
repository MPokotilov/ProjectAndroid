package mainPackage.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.officehoursreservationsystem.R
import com.google.firebase.firestore.auth.User
import mainPackage.utils.Checks
import mainPackage.utils.utils1
import mainPackage.viewModel.CustomViewModelStoreOwner
import mainPackage.viewModel.OHRViewModel

class AddOfficeHoursActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_office_hours)

        val returnButton = findViewById<ImageButton>(R.id.image_button_add_office_hours)
        returnButton.id = R.id.image_button_add_office_hours
        val addButton = findViewById<Button>(R.id.apply_button)
        addButton.id = R.id.apply_button
        val cancelButton = findViewById<Button>(R.id.cancel_button)
        cancelButton.id = R.id.cancel_button
        val buttonList = arrayListOf(addButton, cancelButton, returnButton)

        for (button in buttonList) {
            val onClickListener = button.setOnClickListener {
                when (it.id) {
                    R.id.apply_button -> {
                        onButtonClickAdd(it)
                    }
                    R.id.cancel_button -> {
                        onButtonClickReturn(it)
                    }
                    R.id.image_button_add_office_hours ->{
                        onButtonClickReturn(it)
                    }
                }
            }
        }
    }

    fun onButtonClickAdd(view: View){
        val customViewModelStoreOwner = CustomViewModelStoreOwner()
        val viewModel = ViewModelProvider(customViewModelStoreOwner).get(OHRViewModel::class.java)
        val titleInput = findViewById<EditText>(R.id.title_input)
        val email = titleInput.text.toString().trim()
        var tempuser: mainPackage.model.User = mainPackage.model.User()
        if(email.isEmpty()){
            showEmptyFieldsPopup(this)
        }
        else if (tempuser.setEmail(email)==Checks.FAILED_CHECK){
            showIncorrectEmailPopup(this)
        }
        else if (tempuser.setEmail(email)==Checks.STUDENT){
            showStudentEmailPopup(this)
        }
        else if (!viewModel.addOfficeHours(email)){
            showIncorrectTeacherEmailPopup(this)
        }
        else{
            val statusText = findViewById<TextView>(R.id.status_add_office_hours)
            statusText.text="Office hours added to the list"
        }
    }

    fun showEmptyFieldsPopup(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage("Please fill all the fields before applying")
        builder.setPositiveButton("OK") { _, _ -> }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun showIncorrectEmailPopup(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage("The email is in incorrect form")
        builder.setPositiveButton("OK") { _, _ -> }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun showStudentEmailPopup(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage("You need to enter a teacher's email")
        builder.setPositiveButton("OK") { _, _ -> }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun showIncorrectTeacherEmailPopup(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage("Teacher not found")
        builder.setPositiveButton("OK") { _, _ -> }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun onButtonClickReturn(view: View){
        val intent = Intent(this, OfficeHoursListActivity::class.java)
        startActivity(intent)
    }
}