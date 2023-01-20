package mainPackage.model

import java.sql.Time
import java.time.DayOfWeek

data class OfficeHoursInstance(val email: String, val timeFrom: String, val timeTo: String, val code: String) {
}