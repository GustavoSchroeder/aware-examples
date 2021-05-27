package com.example.aware

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import androidx.annotation.NonNull
import com.aware.Aware
import com.aware.Aware_Preferences
import com.aware.providers.*
import com.aware.utils.DatabaseHelper
import com.aware.utils.StudyUtils.resetLogs
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import org.json.JSONObject


class MainActivity: FlutterActivity() {
    private val CHANNEL = "ch/aware"

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
            call, result ->
            if(call.method == "accelerometer") {
                var ret = ""
                println(" ====> " + ret);
                val accelerometerData: Cursor? = contentResolver.query(Accelerometer_Provider.Accelerometer_Data.CONTENT_URI, null, null, null, Accelerometer_Provider.Accelerometer_Data.TIMESTAMP + " DESC LIMIT 1000")
                if (accelerometerData != null && accelerometerData.getCount() > 0) {

                    ret = DatabaseHelper.cursorToString(accelerometerData)
                    println(" ====> " + ret);
                } else {
                    println(" Nope");
                }
                if (accelerometerData != null && !accelerometerData.isClosed()) accelerometerData.close()

                result.success(ret)
            }
            if(call.method == "battery") {
                var ret = ""
                val batteryData: Cursor? = contentResolver.query(Battery_Provider.Battery_Data.CONTENT_URI, null, null, null, Battery_Provider.Battery_Data.TIMESTAMP + " DESC LIMIT 1000")
                if (batteryData != null && batteryData.getCount() > 0) {

                    ret = DatabaseHelper.cursorToString(batteryData)
                } else {
                    println("Nope Bateria");
                }
                if (batteryData != null && !batteryData.isClosed()) batteryData.close()

                result.success(ret)
            }
            if(call.method == "location") {
                var ret = ""
                val locationData: Cursor? = contentResolver.query(Locations_Provider.Locations_Data.CONTENT_URI, null, null, null, Locations_Provider.Locations_Data.TIMESTAMP + " DESC LIMIT 1000")
                if (locationData != null && locationData.getCount() > 0) {

                    ret = DatabaseHelper.cursorToString(locationData)
                }
                if (locationData != null && !locationData.isClosed()) locationData.close()

                result.success(ret)
            }
            if (call.method == "application") {
                var ret = ""
                val applicationData: Cursor? = contentResolver.query(
                    Applications_Provider.Applications_Foreground.CONTENT_URI, null, null, null, 
                    Locations_Provider.Locations_Data.TIMESTAMP + " DESC LIMIT 1000")
                if (applicationData != null && applicationData.getCount() > 0) {

                    ret = DatabaseHelper.cursorToString(applicationData)
                }
                if (applicationData != null && !applicationData.isClosed()) applicationData.close()

                result.success(ret)
            }
            if (call.method == "schedule") {
                var ret = ""
                val scheduleData: Cursor? = contentResolver.query(
                    Scheduler_Provider.Scheduler_Data.CONTENT_URI, null, null, null,
                    Scheduler_Provider.Scheduler_Data.TIMESTAMP + " DESC LIMIT 1000")
                if (scheduleData != null && scheduleData.getCount() > 0) {

                    ret = DatabaseHelper.cursorToString(scheduleData)
                }
                if (scheduleData != null && !scheduleData.isClosed()) scheduleData.close()

                result.success(ret)
            }
            if (call.method == "sync") {
                val sync = Intent(Aware.ACTION_AWARE_SYNC_DATA)
                sendBroadcast(sync)
                println("sync....")
            }
            if (call.method == "init"){

                Aware.startAWARE(applicationContext) //initialise core AWARE service
                Aware.setSetting(applicationContext, Aware_Preferences.STATUS_ACCELEROMETER, true)
                Aware.setSetting(applicationContext, Aware_Preferences.STATUS_BATTERY, true)
                Aware.setSetting(applicationContext, Aware_Preferences.STATUS_APPLICATIONS, true)
                Aware.setSetting(applicationContext, Aware_Preferences.STATUS_NOTIFICATIONS, true)
                Aware.startAccelerometer(applicationContext)
                Aware.startBattery(applicationContext)
                Aware.setSetting(applicationContext, Aware_Preferences.DEVICE_ID, "Leo")

                var stdData: JSONObject = JSONObject("{\"study_key\":\"OPA\",\"study_number\":1,\"study_name\":\"LEONARDO AWARE\",\"study_active\":true,\"study_start\":1620959595598,\"study_description\":\"This is a demo study to test AWARE Micro\",\"researcher_first\":\"First Name\",\"researcher_last\":\"Last Name\",\"researcher_contact\":\"your@email.com\"}")
                var studyCfg: String = "[{\"sensors\":[{\"setting\":\"status_accelerometer\",\"value\":\"true\"},{\"setting\":\"frequency_accelerometer\",\"value\":\"200000\"},{\"setting\":\"threshold_accelerometer\",\"value\":\"0\"},{\"setting\":\"frequency_accelerometer_enforce\",\"value\":\"false\"},{\"setting\":\"status_applications\",\"value\":\"true\"},{\"setting\":\"frequency_applications\",\"value\":\"0\"},{\"setting\":\"status_installations\",\"value\":\"false\"},{\"setting\":\"status_keyboard\",\"value\":\"false\"},{\"setting\":\"mask_keyboard\",\"value\":\"false\"},{\"setting\":\"status_notifications\",\"value\":\"false\"},{\"setting\":\"status_crashes\",\"value\":\"false\"},{\"setting\":\"status_barometer\",\"value\":\"false\"},{\"setting\":\"frequency_barometer\",\"value\":\"200000\"},{\"setting\":\"threshold_barometer\",\"value\":\"0\"},{\"setting\":\"frequency_barometer_enforce\",\"value\":\"false\"},{\"setting\":\"status_battery\",\"value\":\"false\"},{\"setting\":\"status_bluetooth\",\"value\":\"false\"},{\"setting\":\"frequency_bluetooth\",\"value\":\"60\"},{\"setting\":\"status_calls\",\"value\":\"false\"},{\"setting\":\"status_messages\",\"value\":\"false\"},{\"setting\":\"status_esm\",\"value\":\"false\"},{\"setting\":\"status_gravity\",\"value\":\"false\"},{\"setting\":\"frequency_gravity\",\"value\":\"200000\"},{\"setting\":\"threshold_gravity\",\"value\":\"0\"},{\"setting\":\"frequency_gravity_enforce\",\"value\":\"false\"},{\"setting\":\"status_gyroscope\",\"value\":\"false\"},{\"setting\":\"frequency_gyroscope\",\"value\":\"200000\"},{\"setting\":\"threshold_gyroscope\",\"value\":\"0\"},{\"setting\":\"frequency_gyroscope_enforce\",\"value\":\"false\"},{\"setting\":\"status_location_gps\",\"value\":\"false\"},{\"setting\":\"frequency_location_gps\",\"value\":\"180\"},{\"setting\":\"min_location_gps_accuracy\",\"value\":\"150\"},{\"setting\":\"status_location_network\",\"value\":\"false\"},{\"setting\":\"frequency_location_network\",\"value\":\"300\"},{\"setting\":\"min_location_network_accuracy\",\"value\":\"1500\"},{\"setting\":\"status_location_passive\",\"value\":\"false\"},{\"setting\":\"location_expiration_time\",\"value\":\"300\"},{\"setting\":\"location_save_all\",\"value\":\"false\"},{\"setting\":\"status_light\",\"value\":\"false\"},{\"setting\":\"frequency_light\",\"value\":\"200000\"},{\"setting\":\"threshold_light\",\"value\":\"0\"},{\"setting\":\"frequency_light_enforce\",\"value\":\"false\"},{\"setting\":\"status_linear_accelerometer\",\"value\":\"false\"},{\"setting\":\"frequency_linear_accelerometer\",\"value\":\"200000\"},{\"setting\":\"threshold_linear_accelerometer\",\"value\":\"0\"},{\"setting\":\"frequency_linear_accelerometer_enforce\",\"value\":\"false\"},{\"setting\":\"status_network_events\",\"value\":\"false\"},{\"setting\":\"status_network_traffic\",\"value\":\"false\"},{\"setting\":\"status_magnetometer\",\"value\":\"false\"},{\"setting\":\"frequency_magnetometer\",\"value\":\"200000\"},{\"setting\":\"threshold_magnetometer\",\"value\":\"0\"},{\"setting\":\"frequency_magnetometer_enforce\",\"value\":\"false\"},{\"setting\":\"status_processor\",\"value\":\"false\"},{\"setting\":\"frequency_processor\",\"value\":\"5\"},{\"setting\":\"status_timezone\",\"value\":\"false\"},{\"setting\":\"status_proximity\",\"value\":\"false\"},{\"setting\":\"frequency_proximity\",\"value\":\"200000\"},{\"setting\":\"threshold_proximity\",\"value\":\"0\"},{\"setting\":\"frequency_proximity_enforce\",\"value\":\"false\"},{\"setting\":\"status_rotation\",\"value\":\"false\"},{\"setting\":\"frequency_rotation\",\"value\":\"200000\"},{\"setting\":\"threshold_rotation\",\"value\":\"0\"},{\"setting\":\"frequency_rotation_enforce\",\"value\":\"false\"},{\"setting\":\"status_screen\",\"value\":\"false\"},{\"setting\":\"status_touch\",\"value\":\"false\"},{\"setting\":\"mask_touch_text\",\"value\":\"false\"},{\"setting\":\"status_significant_motion\",\"value\":\"false\"},{\"setting\":\"status_temperature\",\"value\":\"false\"},{\"setting\":\"frequency_temperature\",\"value\":\"200000\"},{\"setting\":\"threshold_temperature\",\"value\":\"0\"},{\"setting\":\"frequency_temperature_enforce\",\"value\":\"false\"},{\"setting\":\"status_telephony\",\"value\":\"false\"},{\"setting\":\"status_wifi\",\"value\":\"false\"},{\"setting\":\"frequency_wifi\",\"value\":\"60\"},{\"setting\":\"status_websocket\",\"value\":\"false\"},{\"setting\":\"status_mqtt\",\"value\":\"false\"},{\"setting\":\"mqtt_port\",\"value\":\"1883\"},{\"setting\":\"mqtt_keep_alive\",\"value\":\"600\"},{\"setting\":\"mqtt_qos\",\"value\":\"2\"},{\"setting\":\"status_webservice\",\"value\":\"true\"},{\"setting\":\"webservice_server\",\"value\":\"http://191.252.218.208:8080/index.php/1/OPA\"},{\"setting\":\"webservice_wifi_only\",\"value\":\"false\"},{\"setting\":\"fallback_network\",\"value\":\"0\"},{\"setting\":\"webservice_charging\",\"value\":\"false\"},{\"setting\":\"frequency_webservice\",\"value\":\"5\"},{\"setting\":\"frequency_clean_old_data\",\"value\":\"0\"},{\"setting\":\"webservice_silent\",\"value\":\"false\"},{\"setting\":\"remind_to_charge\",\"value\":\"false\"},{\"setting\":\"foreground_priority\",\"value\":\"true\"},{\"setting\":\"debug_flag\",\"value\":\"true\"},{\"setting\":\"debug_tag\",\"value\":\"AWARE\"},{\"setting\":\"debug_db_slow\",\"value\":\"false\"},{\"setting\":\"webservice_simple\",\"value\":\"false\"},{\"setting\":\"webservice_remove_data\",\"value\":\"false\"},{\"setting\":\"interface_locked\",\"value\":\"false\"},{\"setting\":\"frequency_enforce_all\",\"value\":\"false\"},{\"setting\":\"study_id\",\"value\":\"OPA\"},{\"setting\":\"study_start\",\"value\":1.620959595598E12}],\"plugins\":[{\"plugin\":\"com.aware.plugin.ambient_noise\",\"settings\":[{\"setting\":\"status_plugin_ambient_noise\",\"value\":\"false\"},{\"setting\":\"frequency_plugin_ambient_noise\",\"value\":\"5\"},{\"setting\":\"plugin_ambient_noise_sample_size\",\"value\":\"30\"},{\"setting\":\"plugin_ambient_noise_silence_threshold\",\"value\":\"50\"},{\"setting\":\"plugin_ambient_noise_no_raw\",\"value\":\"true\"}]},{\"plugin\":\"com.aware.plugin.google.activity_recognition\",\"settings\":[{\"setting\":\"status_plugin_google_activity_recognition\",\"value\":\"false\"},{\"setting\":\"frequency_plugin_google_activity_recognition\",\"value\":\"60\"}]},{\"plugin\":\"com.aware.plugin.sentimental\",\"settings\":[{\"setting\":\"status_plugin_sentimental\",\"value\":\"false\"}]},{\"plugin\":\"com.aware.plugin.openweather\",\"settings\":[{\"setting\":\"status_plugin_openweather\",\"value\":\"false\"},{\"setting\":\"units_plugin_openweather\",\"value\":\"metric\"},{\"setting\":\"plugin_openweather_frequency\",\"value\":\"60\"},{\"setting\":\"api_key_plugin_openweather\",\"value\":\"ada11fb870974565377df238f3046aa9\"}]},{\"plugin\":\"com.aware.plugin.esm.scheduler\",\"settings\":[{\"setting\":\"status_plugin_esm_scheduler\",\"value\":\"false\"}]},{\"plugin\":\"com.aware.plugin.fitbit\",\"settings\":[{\"setting\":\"status_plugin_fitbit\",\"value\":\"false\"},{\"setting\":\"units_plugin_fitbit\",\"value\":\"metric\"},{\"setting\":\"fitbit_granularity\",\"value\":\"15min\"},{\"setting\":\"fitbit_hr_granularity\",\"value\":\"1sec\"},{\"setting\":\"plugin_fitbit_frequency\",\"value\":\"15\"},{\"setting\":\"api_key_plugin_fitbit\",\"value\":\"227YG3\"},{\"setting\":\"api_secret_plugin_fitbit\",\"value\":\"033ed2a3710c0cde04343d073c09e378\"}]},{\"plugin\":\"com.aware.plugin.sensortag\",\"settings\":[{\"setting\":\"status_plugin_sensortag\",\"value\":\"false\"},{\"setting\":\"frequency_plugin_sensortag\",\"value\":\"30\"}]},{\"plugin\":\"com.aware.plugin.contacts_list\",\"settings\":[{\"setting\":\"status_plugin_contacts\",\"value\":\"false\"},{\"setting\":\"frequency_plugin_contacts\",\"value\":\"1\"}]},{\"plugin\":\"com.aware.plugin.google.auth\",\"settings\":[{\"setting\":\"status_plugin_google_login\",\"value\":\"false\"}]},{\"plugin\":\"com.aware.plugin.google.fused_location\",\"settings\":[{\"setting\":\"status_google_fused_location\",\"value\":\"false\"},{\"setting\":\"frequency_google_fused_location\",\"value\":\"300\"},{\"setting\":\"max_frequency_google_fused_location\",\"value\":\"60\"},{\"setting\":\"fallback_location_timeout\",\"value\":\"20\"},{\"setting\":\"location_sensitivity\",\"value\":\"5\"},{\"setting\":\"accuracy_google_fused_location\",\"value\":\"102\"}]},{\"plugin\":\"com.aware.plugin.device_usage\",\"settings\":[{\"setting\":\"status_plugin_device_usage\",\"value\":\"false\"}]},{\"plugin\":\"com.aware.plugin.studentlife.audio_final\",\"settings\":[{\"setting\":\"status_plugin_studentlife_audio\",\"value\":\"false\"},{\"setting\":\"plugin_conversations_delay\",\"value\":\"1\"},{\"setting\":\"plugin_conversations_off_duty\",\"value\":\"3\"},{\"setting\":\"plugin_conversations_length\",\"value\":\"1\"}]}]}]"

                val studyData = ContentValues()
                studyData.put(
                    Aware_Provider.Aware_Studies.STUDY_DEVICE_ID, Aware.getSetting(
                        applicationContext, Aware_Preferences.DEVICE_ID
                    )
                )
                studyData.put(
                    Aware_Provider.Aware_Studies.STUDY_TIMESTAMP,
                    System.currentTimeMillis()
                )
                studyData.put(Aware_Provider.Aware_Studies.STUDY_KEY, 1)
                studyData.put(Aware_Provider.Aware_Studies.STUDY_API, "OPA")
                studyData.put(Aware_Provider.Aware_Studies.STUDY_URL, "http://191.252.218.208:8080")
                studyData.put(
                    Aware_Provider.Aware_Studies.STUDY_PI,
                    stdData.getString("researcher_first")
                        .toString() + " " + stdData.getString("researcher_last") + "\nContact: " + stdData.getString(
                        "researcher_contact"
                    )
                )
                studyData.put(Aware_Provider.Aware_Studies.STUDY_CONFIG, studyCfg)
                studyData.put(
                    Aware_Provider.Aware_Studies.STUDY_TITLE,
                    stdData.getString("study_name")
                )
                studyData.put(
                    Aware_Provider.Aware_Studies.STUDY_DESCRIPTION,
                    stdData.getString("study_description")
                )
                studyData.put(
                    Aware_Provider.Aware_Studies.STUDY_JOINED,
                    System.currentTimeMillis()
                )
                studyData.put(Aware_Provider.Aware_Studies.STUDY_EXIT, 0);

                contentResolver.insert(Aware_Provider.Aware_Studies.CONTENT_URI, studyData)

                //Aware.setSetting(applicationContext, Aware_Preferences.STATUS_ACCELEROMETER, true)

                resetLogs(applicationContext)
                val aware = Intent(applicationContext, Aware::class.java)
                context.startService(aware)

                val sync = Intent(Aware.ACTION_AWARE_SYNC_DATA)
                context.sendBroadcast(sync)

            }
        }
    }
}