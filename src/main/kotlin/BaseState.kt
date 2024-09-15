import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class BaseState {
    private val job = SupervisorJob()
    val baseScope = CoroutineScope(job)

    fun clear() {
        baseScope.cancel()
    }
}
