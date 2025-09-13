import java.io.File
import java.util.*

data class Entry(val site: String, val username: String, val password: String)

object PasswordManager {
    private val file = File("passwords.txt")
    private val entries = mutableListOf<Entry>()

    init {
        if (file.exists()) {
            file.readLines().forEach {
                val parts = it.split("|")
                if (parts.size == 3) entries.add(Entry(parts[0], parts[1], parts[2]))
            }
        }
    }

    fun add(site: String, username: String, password: String) {
        entries.add(Entry(site, username, password))
        file.appendText("$site|$username|$password\n")
    }

    fun list() {
        println("All passwords:")
        entries.forEachIndexed { i, e ->
            println("${i + 1}. ${e.site} / ${e.username} / ${"*".repeat(e.password.length)}")
        }
    }

    fun get(site: String) {
        val e = entries.find { it.site.equals(site, true) }
        if (e != null) println("Password for $site: ${e.password}") else println("Not found!")
    }

    fun menu() {
        val sc = Scanner(System.`in`)
        while (true) {
            println("1. Add 2. List 3. Get 4. Exit")
            when (sc.nextInt()) {
                1 -> {
                    print("Site: "); val s = sc.next()
                    print("Username: "); val u = sc.next()
                    print("Password: "); val p = sc.next()
                    add(s, u, p)
                }
                2 -> list()
                3 -> {
                    print("Site: "); val s = sc.next()
                    get(s)
                }
                4 -> break
            }
        }
    }
}

fun main() {
    println("Password Manager")
    PasswordManager.menu()
}