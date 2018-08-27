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

package co.avaldes.retipy.domain.diagnostic

import com.fasterxml.jackson.databind.ObjectMapper

data class Roi(
    var roi_x: List<Int> = emptyList(),
    var roi_y: List<Int> = emptyList(),
    var notes: String = "")
{
    override fun toString(): String
    {
        val objectMapper = ObjectMapper()
        return objectMapper.writeValueAsString(this)
    }

    companion object
    {
        const val SEPARATOR = "|||"

        fun fromPersistence(string: String): List<Roi> {
            val objectMapper = ObjectMapper()
            return string.split(SEPARATOR).map { objectMapper.readValue(it, Roi::class.java) }
        }

        fun toPersistence(rois: List<Roi>): String {
            val stringBuilder = StringBuilder()
            for (i in rois.indices)
            {
                stringBuilder.append(rois[i].toString())
                if (i < rois.size - 1)
                {
                    stringBuilder.append(SEPARATOR)
                }
            }
            return stringBuilder.toString()
        }

        fun fromString(string: String): Roi {
            val objectMapper = ObjectMapper()
            return objectMapper.readValue(string, Roi::class.java)
        }
    }
}
