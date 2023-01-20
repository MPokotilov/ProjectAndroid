package mainPackage.utils

import java.util.regex.Pattern

const val PASSWORD_PATTERN = "^(?=.*[a-z]).{5,}\$"

object utils1 {

    fun passwordCheck(password: String): Checks{
        var pattern = Pattern.compile(PASSWORD_PATTERN)
        var matcher = pattern.matcher(password)
        when(matcher.matches()) {
            true -> return Checks.PASSED
            false -> return Checks.INCORRECT_PASSWORD_FORM
        }
    }

    fun timeFormCheck(timeRange: String): Boolean {
        val pattern = "^(\\d{2}):(\\d{2})-(\\d{2}):(\\d{2})$".toRegex()
        if (!pattern.matches(timeRange)) {
            return false
        }
        val (startHour, startMinute, endHour, endMinute) = pattern.find(timeRange)!!.destructured
        val startTime = startHour.toInt() * 60 + startMinute.toInt()
        val endTime = endHour.toInt() * 60 + endMinute.toInt()
        return startTime < endTime
    }

}

enum class Checks {
    TEACHER, STUDENT, FAILED_CHECK, INCORRECT_PASSWORD_FORM, PASSWORD_DOESNT_MATCH, INCORRECT_EMAIL_FORM, PASSED, NEW_USER_CREATED
}