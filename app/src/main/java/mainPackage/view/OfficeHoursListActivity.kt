package mainPackage.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.lifecycle.ViewModelProvider
import com.example.officehoursreservationsystem.R
import mainPackage.model.OfficeHoursInstance
import mainPackage.viewModel.OHRViewModel

class OfficeHoursListActivity : AppCompatActivity() {
    private lateinit var viewModel: OHRViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_office_hours_list)
        viewModel = ViewModelProvider(this).get(OHRViewModel::class.java)

        val secondButAct = findViewById<ImageButton>(R.id.image_button_2)
        secondButAct.setOnClickListener{
            val Intent = Intent(this, Main2Activity::class.java)
            startActivity(Intent)
        }

        val requestListButton = findViewById<ImageButton>(R.id.img_button_forward)
        requestListButton.setOnClickListener{
            val Intent = Intent(this, RequestListActivity::class.java)
            startActivity(Intent)
        }

        val addOfficeHoursButton = findViewById<Button>(R.id.addOfficeHoursButton)
        if (viewModel.currentUser.isATeacher == false){
            addOfficeHoursButton.setOnClickListener{
                val Intent = Intent(this, CreateOfficeHoursActivity::class.java)
                startActivity(Intent)
            }
        }
        else{
            addOfficeHoursButton.setOnClickListener{
                val Intent = Intent(this, AddOfficeHoursActivity::class.java)
                startActivity(Intent)
            }
        }

        val myLinearLayout = findViewById<LinearLayout>(R.id.my_linear_layout)
//        val myScrollView = findViewById<ScrollView>(R.id.scrollView3)
        val myList = listOf(OfficeHoursInstance("maksym@gmail.com", "10:00", "11:00", "1231"), OfficeHoursInstance("jane@gmail.com", "11:00", "12:00", "1231"))
//        val myList = viewModel.getOfficeHoursList()

        if (myList != null) {
            for (item in myList) {
                val button = Button(this)
                button.text = "${item.email} ${item.timeFrom} ${item.timeTo}"
                    button.setOnClickListener {
                        val intent = Intent(this, RequestCreationActivity::class.java)
                        intent.putExtra("ITEM_ID", item.code)
                        startActivity(intent)
                }
                myLinearLayout.addView(button)
            }
        }
//        myScrollView.addView(myLinearLayout)
    }


}