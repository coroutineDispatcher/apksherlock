import kotlinx.coroutines.*

abstract class BaseState {
    private val job = SupervisorJob()
    val baseScope = CoroutineScope(job)

    fun clear() {
        baseScope.cancel()
    }
}
