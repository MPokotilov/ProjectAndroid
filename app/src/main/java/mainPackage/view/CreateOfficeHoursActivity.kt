package mainPackage.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.officehoursreservationsystem.R
import mainPackage.utils.Checks
import mainPackage.utils.utils1
import mainPackage.viewModel.CustomViewModelStoreOwner
import mainPackage.viewModel.OHRViewModel

class CreateOfficeHoursActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_office_hours)
        val returnButton = findViewById<ImageButton>(R.id.image_button_teacher_creation)
        returnButton.id = R.id.image_button_add_office_hours
        val createButton = findViewById<Button>(R.id.apply_button)
        createButton.id = R.id.apply_button
        val cancelButton = findViewById<Button>(R.id.cancel_button)
        cancelButton.id = R.id.cancel_button
        val buttonList = arrayListOf(createButton, cancelButton, returnButton)

        for (button in buttonList) {
            val onClickListener = button.setOnClickListener {
                when (it.id) {
                    R.id.apply_button -> {
                        onButtonClickAdd(it)
                    }
                    R.id.cancel_button -> {
                        onButtonClickReturn(it)
                    }
                    R.id.image_button_teacher_creation ->{
                        onButtonClickReturn(it)
                    }
                }
            }
        }
    }

    fun onButtonClickAdd(view: View){
        val customViewModelStoreOwner = CustomViewModelStoreOwner()
        val viewModel = ViewModelProvider(customViewModelStoreOwner).get(OHRViewModel::class.java)
        val timeInput = findViewById<EditText>(R.id.title_input)
        val time = timeInput.text.toString().trim()
        if(time.isEmpty()){
            showEmptyFieldsPopup(this)
        }
        else if (!utils1.timeFormCheck(time)){
            showIncorrectTimePopup(this)
        }
        else{
            viewModel.createOfficeHours(time)
            showOHAddedPopup(this)
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

    fun showOHAddedPopup(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Office hours created")
        builder.setMessage("Your office hours have been added to your list!")
        builder.setPositiveButton("OK") { _, _ -> }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun showIncorrectTimePopup(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage("The time is in incorrect form")
        builder.setPositiveButton("OK") { _, _ -> }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun onButtonClickReturn(view: View){
        val intent = Intent(this, OfficeHoursListActivity::class.java)
        startActivity(intent)
    }
}