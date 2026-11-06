// Guardian.kt
// CSE 310 - Module #1 (Language: Kotlin)
// A CLI Safety Engine for parents to monitor their child's digital and physical safety.

// Data Class to store incoming text messages
data class Message(val sender: String, val text: String)

// Data Class to store call logs
data class CallLog(val caller: String, val durationMins: Int)

fun main() {
    println("=====================================")
    println("       WELCOME TO GUARDIAN           ")
    println("=====================================")
    
    // val is used for fixed data that will not change
    val childName = "Leo" 
    println("Monitoring device for: $childName\n")

    // Collections: MutableLists to store logs
    val textHistory = mutableListOf<Message>()
    val callHistory = mutableListOf<CallLog>()
    
    // Collections: Map to store environmental sensor readings
    val environmentSensors = mutableMapOf("Temperature" to 28.5, "Noise Level" to "Low")
    
    // var is used because this data will change
    var currentGPSLocation: String? = "Home (Area 10, Lilongwe)" 
    var screenTimeMins = 45
    var isInDanger = false

    // Simulate initial data
    textHistory.add(Message("Uncle Banda", "Are you home? I am coming to visit."))
    callHistory.add(CallLog("Grandma", 15))
    
    // Control Flow: while loop to keep the dashboard running
    var isRunning = true
    while (isRunning) {
        println("\n--- GUARDIAN DASHBOARD ---")
        println("1. View Recent Chats")
        println("2. View Call Logs")
        println("3. Check Screen Time")
        println("4. View GPS Location")
        println("5. Read Environment Sensors")
        println("6. View Danger Alerts")
        println("7. Simulate Incoming Text")
        println("8. Exit")

        print("\nEnter your choice: ")
        val input = readLine()

        // Null Safety: Using ?. (safe call) and ?: (elvis operator) in case input is null
        val choice = input?.trim() ?: "0"

        // Control Flow: when statement for the menu
        when (choice) {
            "1" -> viewChats(textHistory)
            "2" -> viewCalls(callHistory)
            "3" -> checkScreenTime(screenTimeMins)
            "4" -> checkLocation(currentGPSLocation)
            "5" -> readEnvironmentSensors(environmentSensors)
            "6" -> {
                isInDanger = triggerDangerAlert(isInDanger, currentGPSLocation, environmentSensors)
            }
            "7" -> {
                println("Enter sender name:")
                val senderInput = readLine()
                println("Enter message:")
                val msgInput = readLine()
                
                // Null Safety: If sender or message is null, provide a default string
                val sender = senderInput?.trim() ?: "Unknown Contact"
                val message = msgInput?.trim() ?: "No message content"
                
                textHistory.add(Message(sender, message))
                println("New message logged from $sender.")
            }
            "8" -> {
                isRunning = false
                println("Exiting Guardian. Stay safe!")
            }
            else -> println("Invalid choice. Please enter a number 1-8.")
        }
    }
}

// Function 1: View Chats
fun viewChats(history: List<Message>) {
    println("\n--- CHAT HISTORY ---")
    if (history.isEmpty()) {
        println("No messages found.")
    } else {
        for (msg in history) {
            println("[${msg.sender}]: ${msg.text}")
        }
    }
}

// Function 2: View Call Logs
fun viewCalls(history: List<CallLog>) {
    println("\n--- CALL HISTORY ---")
    if (history.isEmpty()) {
        println("No calls found.")
    } else {
        for (call in history) {
            println("Called ${call.caller} for ${call.durationMins} mins.")
        }
    }
}

// Function 3: Check Screen Time
fun checkScreenTime(minutes: Int) {
    println("\n--- SCREEN TIME ---")
    println("Child has used the device for $minutes minutes today.")
    if (minutes > 120) {
        println("WARNING: Screen time exceeds 2 hours.")
    } else {
        println("Screen time is within limits.")
    }
}

// Function 4: Check GPS Location (Demonstrates Null Safety)
fun checkLocation(location: String?) {
    println("\n--- GPS LOCATION ---")
    // Safe call operator (?.) and Elvis (?:) used here
    val safeLocation = location ?: "Location Unavailable (GPS Signal Lost)"
    println("Current Location: $safeLocation")
}

// Function 5: Read Environment Sensors
fun readEnvironmentSensors(sensors: Map<String, Any>) {
    println("\n--- ENVIRONMENT SENSORS ---")
    for ((sensorName, reading) in sensors) {
        println("$sensorName: $reading")
    }
}

// Function 6: Trigger Danger Alert
fun triggerDangerAlert(isCurrentlyInDanger: Boolean, location: String?, sensors: Map<String, Any>): Boolean {
    println("\n--- DANGER ALERTS ---")
    
    // Checking for danger using boolean and sensor data
    val temp = sensors["Temperature"]
    var dangerDetected = isCurrentlyInDanger

    if (temp is Double && temp > 35.0) {
        println("ALERT: High temperature detected ($temp°C)! Child may be outdoors in extreme heat.")
        dangerDetected = true
    }
    
    if (location == null) {
        println("ALERT: GPS signal lost. Cannot track child location!")
        dangerDetected = true
    }

    if (dangerDetected) {
        println("STATUS: DANGER DETECTED! Sending signal to parent...")
    } else {
        println("STATUS: Safe. No danger detected.")
    }
    
    return dangerDetected
}