package com.poslifayproject.poslifay.model

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.sql.Date
import java.sql.Types
import java.util.UUID
@Entity
data class Users(
    @Id

    @JdbcTypeCode(Types.VARCHAR)
    val id: UUID? = UUID.randomUUID(),
    val firstName:String?,
    private val lastName : String?,
    @Column(unique = true, name = "username")
    private val username: String?,
    @Column(unique = true)
     val email: String?,
    private val password: String?,
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "comment_id")
    val comments : List<Comment>?,
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val likes:List<Like>?,
    val birthDate: Date?,
    val createDate: Date?,
    val sex:Boolean,
    val age:Short,
    val userImageUrl:String?,
    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER) // EAGER önerilir
    @CollectionTable(
        name = "user_authorities",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")]
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "authority", nullable = false) // null olmayacak şekilde işaretle
    var authorities: MutableSet<Role> = mutableSetOf(Role.ROLE_USER),
     var isAccountNonExpired: Boolean? = true,
    var enabled: Boolean ?= true,
    var accountNonLocked: Boolean? = true,
    var credentialsNonExpired: Boolean ?= true,
): UserDetails {

    constructor() : this(
        id = UUID.randomUUID(),
        firstName = null,
        lastName = null,
        username = null,
        email = null,
        password = null,
        comments = emptyList(),
        likes = emptyList(),
        birthDate = null,
        createDate = Date(System.currentTimeMillis()),
        authorities= mutableSetOf(Role.ROLE_USER),
        isAccountNonExpired = true,
        enabled = true,
        accountNonLocked = true,
        credentialsNonExpired = true,
        sex=false,
        age=0,
        userImageUrl=null

    )
    constructor(
        firstName: String,
        lastName: String,
        userName: String,
        email: String,
        password: String,
        birthDate: Date,
        sex: Boolean,
        userImage:String,
        age: Short
    ) : this(
        firstName = firstName,
        lastName = lastName,
        username  = userName,
        email = email,
        password = password,
        comments = emptyList(), // Eksik alanları ekleyin
        likes = emptyList(),
        birthDate = birthDate,
        createDate = Date(System.currentTimeMillis()),
        authorities =  mutableSetOf(Role.ROLE_USER),
        isAccountNonExpired = true,
        enabled = true,
        accountNonLocked = true,
        credentialsNonExpired = true,
        sex=sex,
        age=age,
        userImageUrl=userImage
    )

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return authorities ?: throw IllegalStateException("Authorities are not set")
    }

    override fun getPassword(): String {
        return password ?: throw IllegalStateException("Password is not set")
    }



    override fun getUsername(): String {
        return username!!
    }
    override fun isAccountNonExpired(): Boolean {
        return isAccountNonExpired!!
    }

    override fun isEnabled(): Boolean = enabled ?: true


    override fun isAccountNonLocked(): Boolean {
        return accountNonLocked!!
    }

    override fun isCredentialsNonExpired(): Boolean {
        return credentialsNonExpired!!
    }
}
