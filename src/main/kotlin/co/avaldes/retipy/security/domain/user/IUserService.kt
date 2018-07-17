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

package co.avaldes.retipy.security.domain.user

/**
 * Interface that defines operations related to the domain object [User].
 */
interface IUserService
{
    /**
     * Gets a [User] by its given id. If no user has the given id, a null value will be returned
     *
     * @param id the unique identifier of the user to be queried
     */
    fun findById(id: Long): User?

    /**
     * Gets a [User] by its username, returns null if no user is found.
     *
     * @param username the username of the [User]
     */
    fun findByUsername(username: String): User?

    /**
     * Deletes the given [User] from the service.
     */
    fun delete(user: User)

    /**
     * Creates a new [User]. The method expects that the [User.id] is set to 0L and that the
     * username is unique, if any of those conditions are not met, a [IllegalArgumentException]
     * will be thrown. Returns the persisted object.
     *
     * Note that the [User.password] should be plain text.
     *
     * @param user the [User] to persist
     * @return a [User] with possible changes from the persistence
     */
    fun createUser(user: User): User

    /**
     * Updates the password of the given [User]. The password must be unencrypted. This method will
     * perform the encryption of the password.
     *
     * @param user the [User] to be modified
     * @param newPassword the new password in plain text to be setted to the given user
     *
     * @throws IllegalArgumentException if the given *user* does not exist.
     */
    fun updatePassword(user: User, newPassword: String): User

    /**
     * Updates the name attribute of the given [User].
     *
     * @param user the [User] to be modified
     * @param name the new name to be stored in the user
     *
     * @throws IllegalArgumentException if the given *user* does not exist.
     */
    fun updateName(user: User, name: String): User

    /**
     * Login the user with the given username and password. If the user does not exist or if the
     * password is invalid, null will be returned.
     *
     * @param username the username to login
     * @param password the password to login
     */
    fun login(username: String, password: String): User?
}