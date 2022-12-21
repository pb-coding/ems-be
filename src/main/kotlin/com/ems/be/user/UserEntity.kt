package com.ems.be.user

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "user_entity", schema = "be")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int? = null,
    @Column(name = "user_name")
    var userName: String,
    @Column(name = "first_name")
    var firstName: String,
    @Column(name = "last_name")
    var lastName: String,
    @Column(name = "email")
    var email: String,
    @Column(name = "active")
    var isActive: Boolean,
    @Column(name = "creation_date")
    var creationDate: LocalDateTime = LocalDateTime.now()
) {
    fun getFullName() = "$firstName, $lastName"
}