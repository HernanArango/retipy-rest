package co.avaldes.retipy.domain.task.system

import co.avaldes.retipy.domain.task.AbstractRESTTask
import org.springframework.http.HttpMethod
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Task to verify if the retipy server is running.
 */
class StatusTask(
    retipyUri: String
) : AbstractRESTTask<Boolean>("StatusTask", "/status", retipyUri, emptyMap()) {
    override fun execute(): Boolean {
        var isRunning: Boolean = false
        try {
            getRequest(HttpMethod.GET).exchange().doOnSuccess { isRunning = true }.block()
        } catch (webClientException: Exception) {
            isRunning = false
            val logger = LoggerFactory.getLogger(javaClass)
            logger.info("Error $webClientException")
        }

        return isRunning
    }
}
