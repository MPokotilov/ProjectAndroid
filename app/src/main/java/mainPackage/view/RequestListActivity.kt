package mainPackage.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.officehoursreservationsystem.R

class RequestListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_list)

        val butAct = findViewById<ImageButton>(R.id.image_button_4)
        butAct.setOnClickListener {
            val Intent = Intent(this, OfficeHoursListActivity::class.java)
            startActivity(Intent)
        }
    }
}