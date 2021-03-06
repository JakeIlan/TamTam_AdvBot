package me.evgen.advbot.db

import me.evgen.advbot.db.local.LocalStorage
import me.evgen.advbot.model.entity.Advert
import me.evgen.advbot.model.entity.User
import org.hibernate.SessionFactory
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.cfg.Configuration


object DBSessionFactoryUtil {
    val sessionFactory: SessionFactory = initSessionFactory()
    val localStorage: LocalStorage = LocalStorage()

    private fun initSessionFactory(): SessionFactory {
        val configuration = Configuration().configure().apply {
            addAnnotatedClass(Advert::class.java)
            addAnnotatedClass(User::class.java)
        }
        val builder = StandardServiceRegistryBuilder().applySettings(configuration.properties)

        return configuration.buildSessionFactory(builder.build())
    }
}