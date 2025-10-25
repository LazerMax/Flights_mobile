import android.util.Log
import androidx.appcompat.app.AppCompatActivity

open class LoggingActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "LIFECYCLE_LOGGER"
    }

    private fun getCurrentActivityName(): String {
        return this::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, ">> ${getCurrentActivityName()} - СОЗДАНА (onCreate)")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, ">> ${getCurrentActivityName()} - СТАРТУЕТ (onStart)")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, ">> ${getCurrentActivityName()} - ПЕРЕЗАПУСК (onRestart)")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, ">> ${getCurrentActivityName()} - АКТИВНА (onResume)")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, ">> ${getCurrentActivityName()} - ПАУЗА (onPause)")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, ">> ${getCurrentActivityName()} - ОСТАНОВЛЕНА (onStop)")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, ">> ${getCurrentActivityName()} - УНИЧТОЖЕНА (onDestroy)")
    }
}