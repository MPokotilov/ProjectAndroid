package mainPackage.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mainPackage.utils.Checks
import java.util.regex.Pattern

const val TEACHER_EMAIL_PATTERN = "^[a-zA-Z]+\\.[a-zA-Z]+@pwr\\.edu\\.pl$"
const val STUDENT_EMAIL_PATTERN = "^[a-zA-Z]+\\.[a-zA-Z]+@student\\.pwr\\.edu\\.pl$"

class User {
    var nameSurname: String? = null
    var email: String? = null
    var password: String? = null
    var isATeacher: Boolean? = null
    val officeHoursList = mutableListOf<OfficeHoursInstance>()
    val officeHours = MutableLiveData<List<OfficeHoursInstance>>()


    fun equals(user: User): Boolean = this.email===user.email

    @JvmName("setNameSurname1")
    fun setNameSurname(nameSurname: String){
        this.nameSurname=nameSurname
    }

    fun addOfficeHours(officeHoursInstance: OfficeHoursInstance){
        officeHoursList.add(officeHoursInstance)
        officeHours.value=officeHoursList
    }

    fun removeOfficeHours(officeHoursInstance: OfficeHoursInstance){
        officeHoursList.remove(officeHoursInstance)
        officeHours.value=officeHoursList
    }

    fun getOfficeHours() = officeHours as LiveData<List<OfficeHoursInstance>>


    fun setEmail(email:String): Checks{
        var teacherPattern = Pattern.compile(TEACHER_EMAIL_PATTERN)
        var matcher1 = teacherPattern.matcher(email)
        var studentPattern = Pattern.compile(STUDENT_EMAIL_PATTERN)
        var matcher2 = studentPattern.matcher(email)
        if (matcher1.matches()){
            this.isATeacher=true
            this.email=email
            return Checks.TEACHER
        }
        else if (matcher2.matches()){
            this.isATeacher=false
            this.email=email
            return Checks.STUDENT
        }
        else return Checks.INCORRECT_EMAIL_FORM
    }
}