package mainPackage.model

import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestoreException
import mainPackage.utils.Checks

const val TAG = "FIRESTORE"

class RepositoryMockup {

        //--------------------------------

    //Firebase functions

    fun writeNewUser(pass: String, email: String, isATeacher: Boolean?) : Task<Void> {
        val database = FirebaseFirestore.getInstance()
        val myRef = database.collection("Users").document(email)

        val newUser = hashMapOf(
            "is_a_teacher" to isATeacher,
            "pass" to pass
        )

        myRef.set(newUser)
            .addOnSuccessListener { Log.d(TAG, "User successfully added") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing user", e) }

        return myRef.set(newUser)
    }

    //CHECKED AND FIXED

    fun userLogin(user: User, callback: (Checks) -> Unit) {
        val database = FirebaseFirestore.getInstance()
        val myRef = database.collection("Users").document(user.email.toString())
        myRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val pass = document.get("pass") as String
                    if (pass == user.password) {
                        callback(Checks.PASSED)
                    } else {
                        callback(Checks.FAILED_CHECK)
                    }
                    Log.d(TAG, "Pass successfully checked")
                } else {
                    callback(Checks.NEW_USER_CREATED)
                    Log.d(TAG, "Is empty")
                    writeNewUser(user.password.toString(), user.email.toString(), user.isATeacher)
                }
            }
            .addOnFailureListener { exception ->
                if (exception is FirebaseFirestoreException) {
                    Log.e(TAG, "Error getting document: ", exception)
                }
                callback(Checks.FAILED_CHECK)
            }
    }


    //CHECKED AND FIXED

    fun readNameSurnameByEmail(email: String, callback: (String) -> Unit){
        val database = FirebaseFirestore.getInstance()
        val myRef = database.collection("Users").document(email)

        myRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val fieldValue = document.get("name_surname") as String
                    callback(fieldValue)
                    Log.d(TAG, "Name and surname successfully read")
                } else {
                    Log.d(TAG, "Is empty")
                }
            }
            .addOnFailureListener { exception ->
                if (exception is FirebaseFirestoreException) {
                    Log.e(TAG, "Error getting document: ", exception)
                }
            }
    }

    //CHECKED

    fun readIsATeacherByEmail(email: String): Boolean {
        val database = FirebaseFirestore.getInstance()
        val myRef = database.collection("Users").document(email)
        var fieldValue = false


        myRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    fieldValue = document.get("is_a_teacher") as Boolean
                    Log.d(TAG, "Name and surname successfully read")
                } else {
                    Log.d(TAG, "Is empty")
                }
            }
            .addOnFailureListener { exception ->
                if (exception is FirebaseFirestoreException) {
                    Log.e(TAG, "Error getting document: ", exception)
                }
            }
        return fieldValue
    }

    //CHECKED

    fun writeOfficeHoursInstance(email: String, timeFrom: String, timeTo: String) {
        val database = FirebaseFirestore.getInstance()
        var id = ""
        val myRef = database.collection("OfficeHoursInstance").document()
        id = myRef.id
        val newInstance = hashMapOf(
            "email" to email,
            "time_from" to timeFrom,
            "time_to" to timeTo,
            "id" to id
        )

        myRef.set(newInstance)
            .addOnSuccessListener { Log.d(TAG, "Instance successfully added") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing Instance", e) }



    }

    //CHECKED AND FIXED

    fun readOfficeHoursInstanceTeacher(email: String, callback: (MutableList<OfficeHoursInstance>) -> Unit){
        var timeFrom = ""
        var timeTo = ""
        var userEmail = ""
        var code = ""
        var list = mutableListOf<OfficeHoursInstance>()
        val database = FirebaseFirestore.getInstance()
        val ref = database.collection("OfficeHoursInstance")
            .whereEqualTo("email", email)
        ref.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    timeFrom = document.get("time_from") as String
                    timeTo = document.get("time_to") as String
                    userEmail = document.get("email") as String
                    code = document.get("id") as String
                    list.add(OfficeHoursInstance(userEmail, timeFrom, timeTo, code))
                    Log.d(TAG, " Time instance successfully read")
                }
                callback(list)
            }
            .addOnFailureListener { exception ->
                if (exception is FirebaseFirestoreException) {
                    Log.e(TAG, "Error getting document: ", exception)
                }
            }
    }

    fun updateUserOfficeHoursList(email: String, code: String){
        val database = FirebaseFirestore.getInstance()
        val ref = database.collection("Users").document(email)
        var list = ""

        ref.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    list = document.get("office_hours_list") as String
                    Log.d(TAG, "office_hours_list successfully read")
                } else {
                    Log.d(TAG, "Is empty")
                }
            }
            .addOnFailureListener { exception ->
                if (exception is FirebaseFirestoreException) {
                    Log.e(TAG, "Error getting document: ", exception)
                }
            }
        if (list.isEmpty()){
            list = code
        }
        else {
            list + ", " + code
        }
        ref.update("office_hours_list", list)
    }

    fun showOfficeHoursList(email: String) : MutableList<OfficeHoursInstance>{
        val database = FirebaseFirestore.getInstance()
        val ref = database.collection("Users").document(email)
        var list = mutableListOf<String>()
        var listFinal = mutableListOf<OfficeHoursInstance>()
        var stringValue = ""

        ref.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    stringValue = document.get("office_hours_list") as String
                    Log.d(TAG, "office_hours_list successfully read")
                } else {
                    Log.d(TAG, "Is empty")
                }
            }
            .addOnFailureListener { exception ->
                if (exception is FirebaseFirestoreException) {
                    Log.e(TAG, "Error getting document: ", exception)
                }
            }
        list = stringValue.split(",").map { it.trim() }.toMutableList()

        for (document in list) {
            var timeFrom = ""
            var timeTo = ""
            var userEmail = ""
            var code = ""
            val newRef = database.collection("OfficeHoursInstance").document(document)
            newRef.get()
                .addOnSuccessListener { document ->
                    timeFrom = document.get("time_from") as String
                    timeTo = document.get("time_to") as String
                    userEmail = document.get("email") as String
                    code = document.get("id") as String
                    listFinal.add(OfficeHoursInstance(userEmail, timeFrom, timeTo, code))
                    Log.d(TAG, " Time instance successfully read")

                }
                .addOnFailureListener { exception ->
                    if (exception is FirebaseFirestoreException) {
                        Log.e(TAG, "Error getting document: ", exception)
                    }
                }
        }
        return listFinal
    }

    fun readStudentsTimeInstance(email: String) : StudentsTimeInstance{
        var title = ""
        var time = ""
        var message = ""
        var status = ""
        var officeHoursCode = ""
        val database = FirebaseFirestore.getInstance()
        val ref = database.collection("Student_Request")
            .whereEqualTo("email", email)
        ref.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    title = document.get("Title") as String
                    time = document.get("Time") as String
                    message = document.get("Message") as String
                    status = document.get("Status") as String
                    officeHoursCode = document.get("office_hours_code") as String

                    Log.d(TAG, " Time instance successfully read")
                }
            }
            .addOnFailureListener { exception ->
                if (exception is FirebaseFirestoreException) {
                    Log.e(TAG, "Error getting document: ", exception)
                }
            }
        return StudentsTimeInstance(title, time, message, status, officeHoursCode)
    }

    fun writeStudentsTimeInstance(title: String, time: String, message: String, officeHoursCode: String) {
        val database = FirebaseFirestore.getInstance()
        val myRef = database.collection("Student_Request")

        val newInstance = hashMapOf(
            "Title" to title,
            "Time" to time,
            "Message" to message,
            "Status" to "Pending",
            "office_hours_code" to officeHoursCode
        )

        myRef.add(newInstance)
            .addOnSuccessListener { Log.d(TAG, "Instance successfully added") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing Instance", e) }

    }


    fun updateUsersPassword(password: String, email: String){
        val database = FirebaseFirestore.getInstance()
        val myRef = database.collection("Users").document(email)

        myRef.update("pass", password)
            .addOnSuccessListener { Log.d(TAG, "Password successfully updated") }
            .addOnFailureListener { e -> Log.w(TAG, "Error in updating a password", e) }
    }
    fun timeFromCode(code: String) : String {
        val database = FirebaseFirestore.getInstance()
        var timeFrom = ""
        var timeTo = ""
        var time = ""
        val ref = database.collection("OfficeHoursInstance")
            .whereEqualTo("id", code)
        ref.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    timeFrom = document.get("time_from") as String
                    timeTo = document.get("time_to") as String
                    Log.d(TAG, " Time instance successfully read")
                }
            }
            .addOnFailureListener { exception ->
                if (exception is FirebaseFirestoreException) {
                    Log.e(TAG, "Error getting document: ", exception)
                }
            }
        time = timeFrom + "-" + timeTo
        return time
    }

    fun addOfficeHoursStud (studEmail: String?, tEmail: String) : Boolean{
        val database = FirebaseFirestore.getInstance()
        val tRef = database.collection("Users").document(tEmail)
        val sRef = database.collection("Users").document(studEmail!!)
        var teacherList = ""
        var studList = ""
        var bool = false
        tRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    bool = true
                    teacherList = document.get("office_hours_list") as String
                    Log.d(TAG, "office_hours_list successfully read")
                } else {
                    Log.d(TAG, "Is empty")

                }
            }
            .addOnFailureListener { exception ->
                if (exception is FirebaseFirestoreException) {
                    Log.e(TAG, "Error getting document: ", exception)
                }
            }
        sRef.get()
            .addOnSuccessListener { doc ->
                if (doc != null) {
                    studList = doc.get("office_hours_list") as String
                    if(!studList.contains(teacherList)){
                        if (studList.isEmpty()){
                            studList = teacherList
                        }
                        else{
                            studList = "$studList, $teacherList"
                        }
                        sRef.update("office_hours_list", studList)
                        Log.d(TAG, "office_hours_list successfully read")
                    }
                }
                else {
                    Log.d(TAG, "Is empty")
                }
            }
            .addOnFailureListener { exception ->
                if (exception is FirebaseFirestoreException) {
                    Log.e(TAG, "Error getting document: ", exception)
                }
            }
        return bool
    }

    //Функция которая меняет статус реквеста Pending...
}