/*
 * Copyright (C) 2018 - Alejandro Valdes
 *
 * This file is part of retipy.
 *
 * retipy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * retipy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with retipy.  If not, see <http://www.gnu.org/licenses/>.
 */

package co.avaldes.retipy.domain.task.drusen

import co.avaldes.retipy.domain.evaluation.automated.RetipyEvaluation
import co.avaldes.retipy.domain.evaluation.automated.RetipyTask
import co.avaldes.retipy.domain.task.AbstractRESTTask
import co.avaldes.retipy.persistence.evaluation.retinal.RetipyEvaluationStatus
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpMethod
import org.springframework.web.reactive.function.BodyInserters

class DrusenClassificationBySizeTask(
    retipyUri: String,
    //configuration: Map<String, String> = mapOf(),
    private val retipyEvaluation: RetipyEvaluation
) : AbstractRESTTask<RetipyEvaluation>(
        RetipyTask.Segmentation.name,
        "/drusenclassificationbysize",
        retipyUri,
        emptyMap()
    ) {
    private data class DrusenClassificationBySizeTaskRequest(val image: String)
    private data class DrusenClassificationBySizeTaskResponse(val drusen: String, val information: String)

    private val logger: Logger = LoggerFactory.getLogger(DrusenClassificationBySizeTask::class.java)
    //private val parameters = addMissingProperties(configuration)

    override fun execute(): RetipyEvaluation {
        
        logger.info("Starting task")
        val requestWithBody = getRequest(
            HttpMethod.POST)
            .body(BodyInserters.fromObject(DrusenClassificationBySizeTaskRequest(retipyEvaluation.image)))
        try {
            val response = requestWithBody.retrieve()
                .bodyToMono(DrusenClassificationBySizeTaskResponse::class.java)
                .block()!!
            retipyEvaluation.image = response.drusen
            retipyEvaluation.information = response.information
            retipyEvaluation.status = RetipyEvaluationStatus.Complete
            logger.info("Completed xx",response.information)
            logger.info("Information : {}",response.information);
        } catch (e: Exception) {
            retipyEvaluation.status = RetipyEvaluationStatus.Error
            logger.info("Failed $e")
        }

        return retipyEvaluation
    }


}
