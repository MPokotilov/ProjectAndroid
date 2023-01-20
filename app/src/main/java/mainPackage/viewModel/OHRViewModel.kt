package mainPackage.viewModel

import androidx.lifecycle.ViewModel
import mainPackage.model.OfficeHoursInstance
import mainPackage.model.User
import mainPackage.utils.Checks
import mainPackage.utils.utils1.passwordCheck
import mainPackage.model.RepositoryMockup
import mainPackage.utils.utils1

class OHRViewModel (): ViewModel(){

    var currentUser = User()
    var currOfficeHoursInstanceID = "null"
    val repo = RepositoryMockup()
//    private val userList = repository.getUserList()

    fun login(email: String, password: String): Checks {
        var currUser = User()
        if (currUser.setEmail(email) == Checks.INCORRECT_EMAIL_FORM) return Checks.INCORRECT_EMAIL_FORM
        else if (passwordCheck(password) == Checks.INCORRECT_PASSWORD_FORM) return Checks.INCORRECT_PASSWORD_FORM
        currUser.password = password
        var final: Checks= Checks.FAILED_CHECK
        repo.userLogin(currUser) { result ->
            when(result) {
                Checks.PASSED -> {
                    final = result
                }
                Checks.FAILED_CHECK -> {
                    final = result
                }
                Checks.NEW_USER_CREATED -> {
                    final = result
                }
                else -> {}
            }

        }
        return final
    }

    fun addOfficeHours(teachersEmail: String): Boolean{
        return repo.addOfficeHoursStud(currentUser.email, teachersEmail)
    }

    fun getOfficeHoursList(): MutableList<OfficeHoursInstance>? {
        return currentUser.email?.let { repo.showOfficeHoursList(it) }
    }

    fun createOfficeHours(time: String){
        val pattern = "^(\\d{5})-(\\d{5})\$".toRegex()
        val (startTime, endTime) = pattern.find(time)!!.destructured
        currentUser.email?.let { repo.writeOfficeHoursInstance(it, startTime, endTime) }
    }

    fun timeOutOfBoundsCheck(code: String, time: String): Boolean{
        val pattern = "^(\\d{2}):(\\d{2})-(\\d{2}):(\\d{2})$".toRegex()
        val (startHour1, startMinute1, endHour1, endMinute1) = pattern.find(time)!!.destructured
        val startTime1 = startHour1.toInt() * 60 + startMinute1.toInt()
        val endTime1 = endHour1.toInt() * 60 + endMinute1.toInt()

        val (startHour2, startMinute2, endHour2, endMinute2) = pattern.find(repo.timeFromCode(code))!!.destructured
        val startTime2 = startHour2.toInt() * 60 + startMinute2.toInt()
        val endTime2 = endHour2.toInt() * 60 + endMinute2.toInt()

        return (startTime1 < startTime2 && endTime1 <= startTime2) || (startTime1 >= endTime2 && endTime1 > endTime2)
    }

    fun addNewRequest(title: String, time: String, message: String, officeHoursCode: String){
        repo.writeStudentsTimeInstance(title, time, message, officeHoursCode)
    }

    }

