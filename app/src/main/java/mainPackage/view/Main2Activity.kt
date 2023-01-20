package mainPackage.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.officehoursreservationsystem.R
import mainPackage.utils.Checks
import mainPackage.viewModel.OHRViewModel

class Main2Activity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        val loginButton = findViewById<Button>(R.id.btn_login)

        loginButton.setOnClickListener { onButtonClick(it) }
        }

    fun onButtonClick(view: View) {
        var viewModel = ViewModelProvider(this).get(OHRViewModel::class.java)

        val emailEditText = findViewById<EditText>(R.id.et_email)
        val passwordEditText = findViewById<EditText>(R.id.et_password)

        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        viewModel.currentUser.isATeacher=true
        when(viewModel.login(email, password)){
//        when(Checks.PASSED){
            Checks.PASSED-> {
                val Intent = Intent(this, OfficeHoursListActivity::class.java)
                startActivity(Intent)
            }
            Checks.INCORRECT_PASSWORD_FORM->showIncorrectPasswordFormPopup(this)
            Checks.INCORRECT_EMAIL_FORM -> showIncorrectEmailFormPopup(this)
            Checks.NEW_USER_CREATED-> {
                showNewUserPopup(this)

                val Intent = Intent(this, OfficeHoursListActivity::class.java)
                startActivity(Intent)
            }
            Checks.FAILED_CHECK-> showIncorrectPasswordPopup(this)
            else->{}
        }
    }

        fun showNewUserPopup(context: Context) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("New User Created")
            builder.setMessage("A new user has been successfully created!")
            builder.setPositiveButton("OK") { _, _ -> }
            val alertDialog = builder.create()
            alertDialog.show()
        }

        fun showIncorrectPasswordFormPopup(context: Context) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Error")
            builder.setMessage("The password is in incorrect form")
            builder.setPositiveButton("OK") { _, _ -> }
            val alertDialog = builder.create()
            alertDialog.show()
        }

        fun showIncorrectPasswordPopup(context: Context) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Error")
            builder.setMessage("The password is incorrect")
            builder.setPositiveButton("OK") { _, _ -> }
            val alertDialog = builder.create()
            alertDialog.show()
        }

        fun showIncorrectEmailFormPopup(context: Context) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Error")
            builder.setMessage("The email is in incorrect form")
            builder.setPositiveButton("OK") { _, _ -> }
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }




