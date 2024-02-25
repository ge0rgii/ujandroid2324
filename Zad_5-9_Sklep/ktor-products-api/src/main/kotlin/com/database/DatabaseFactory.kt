package com.database

import Orders
import com.utils.HashingUtil

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction


object DatabaseFactory {
    fun init() {
        Database.connect("jdbc:sqlite:mydb.db", driver = "org.sqlite.JDBC")
        transaction {
            SchemaUtils.create(Categories, Products)

            // Dodawanie kategorii
            Categories.insert {
                it[id] = 1
                it[name] = "Telefony"
                it[description] = "Telefony komórkowe"
                it[imageUrl] = "https://media.cdn.videotesty.pl/media/content/2018/02/04/apple-iphone-4-kolor-biay.jpg"
            }

            Categories.insert {
                it[id] = 2
                it[name] = "Słuchawki"
                it[description] = "Urządzenia audio"
                it[imageUrl] = "https://ilounge.pl/files/products/Beats-by-Dr.Dre-Solo-2-Wireless-White.1000x.jpg"
            }

            Categories.insert {
                it[id] = 3
                it[name] = "Tablety"
                it[description] = "Przenośne urządzenia do przeglądania internetu i multimediów"
                it[imageUrl] = "https://www.x-kom.pl/res/products/2020/9/15/605987/apple-ipad-10-2-32gb-wifi-silver.jpg" // Zmieniony link
            }

            Categories.insert {
                it[id] = 4
                it[name] = "Konsole do gier"
                it[description] = "Urządzenia do gier wideo dla domowej rozrywki"
                it[imageUrl] = "https://www.x-kom.pl/res/products/2020/11/19/614604/sony-playstation-5.jpg" // Zmieniony link
            }

            // Dodawanie produktów
            // Telefony
            Products.insert {
                it[name] = "Iphone 14"
                it[price] = 5000.00
                it[info] = "Nowoczesny design i zaawansowana technologia."
                it[categoryId] = 1
                it[imageUrl] = "https://www.x-kom.pl/res/products/2020/10/14/610050/apple-iphone-12-pro-128gb-pacific-blue.jpg" // Zmieniony link
            }

            Products.insert {
                it[name] = "Nokia 3310"
                it[price] = 250.00
                it[info] = "Legendarny telefon, teraz jeszcze trwalszy."
                it[categoryId] = 1
                it[imageUrl] = "https://media.cdn.videotesty.pl/media/cache/b5/29/b5297efe5c323ce865aeef1190642103.jpg"
            }

            Products.insert {
                it[name] = "Samsung Galaxy S21"
                it[price] = 4000.00
                it[info] = "Rewolucyjny aparat i niezrównana wydajność."
                it[categoryId] = 1
                it[imageUrl] = "https://www.x-kom.pl/res/products/2021/1/14/619929/samsung-galaxy-s21-5g-256gb-phantom-gray.jpg" // Zmieniony link
            }

            // Słuchawki
            Products.insert {
                it[name] = "Google Buds"
                it[price] = 500.00
                it[info] = "Krystalicznie czysty dźwięk i komfort noszenia."
                it[categoryId] = 2
                it[imageUrl] = "https://image.ceneostatic.pl/data/products/131929772/i-google-pixel-buds-pro-pomaranczowe.jpg"
            }

            Products.insert {
                it[name] = "Sony WH-1000XM4"
                it[price] = 1500.00
                it[info] = "Najlepsza redukcja szumów na rynku."
                it[categoryId] = 2
                it[imageUrl] = "https://www.x-kom.pl/res/products/2020/8/6/599833/sony-wh-1000xm4-silver.jpg" // Zmieniony link
            }

            Products.insert {
                it[name] = "Bose QuietComfort 35 II"
                it[price] = 1200.00
                it[info] = "Niesamowity komfort i jakość dźwięku."
                it[categoryId] = 2
                it[imageUrl] = "https://www.x-kom.pl/res/products/2017/9/21/172932/bose-quietcomfort-35-ii-silver.jpg" // Zmieniony link
            }

            // Tablety
            Products.insert {
                it[name] = "iPad Pro"
                it[price] = 3500.00
                it[info] = "Moc komputera w formie tabletu."
                it[categoryId] = 3
                it[imageUrl] = "https://www.x-kom.pl/res/products/2020/3/19/579139/apple-ipad-pro-11-2020-128gb-wifi-silver.jpg" // Zmieniony link
            }

            Products.insert {
                it[name] = "Samsung Galaxy Tab S7"
                it[price] = 2700.00
                it[info] = "Wszechstronny tablet do pracy i zabawy."
                it[categoryId] = 3
                it[imageUrl] = "https://www.x-kom.pl/res/products/2020/8/5/599421/samsung-galaxy-tab-s7-11-128gb-wifi-mystic-black.jpg" // Zmieniony link
            }

            Products.insert {
                it[name] = "Lenovo Tab P11"
                it[price] = 1200.00
                it[info] = "Idealny balans ceny i wydajności."
                it[categoryId] = 3
                it[imageUrl] = "https://www.x-kom.pl/res/products/2021/1/12/619472/lenovo-tab-p11-4-64gb-wifi-slate-gray.jpg" // Zmieniony link
            }
// Konsole do gier
            Products.insert {
                it[name] = "PlayStation 5"
                it[price] = 5000.00
                it[info] = "Najnowsza generacja rozrywki domowej."
                it[categoryId] = 4
                it[imageUrl] = "https://example.com/playstation_5.jpg"
            }

            Products.insert {
                it[name] = "Xbox Series X"
                it[price] = 4500.00
                it[info] = "Niezrównana moc i wydajność."
                it[categoryId] = 4
                it[imageUrl] = "https://www.x-kom.pl/res/products/2020/11/19/614604/sony-playstation-5.jpg" // Zmieniony link
            }

            Products.insert {
                it[name] = "Nintendo Switch"
                it[price] = 2000.00
                it[info] = "Graj gdzie chcesz, jak chcesz."
                it[categoryId] = 4
                it[imageUrl] = "https://www.amazon.pl/Nintendo-Switch-konsola-model-bialy/dp/B098RJXBTY" // Zmieniony link
            }
            SchemaUtils.create(Users)
            if (Users.selectAll().count() == 0L) {
                Users.insert {
                    it[email] = "admin"
                    it[hashedPassword] = HashingUtil.hashPassword("admin")
                    it[firstName] = "Jan" // zmieniono imię na polskie
                    it[lastName] = "Kowalski" // zmieniono nazwisko na polskie
                    it[address] = "ul. Warszawska 10, 00-001 Warszawa" // zmieniono adres na polski
                }
                Users.insert {
                    it[email] = "user"
                    it[hashedPassword] = HashingUtil.hashPassword("user")
                    //add other fields defined for User class
                    it[firstName] = "Anna" // zmieniono imię na polskie
                    it[lastName] = "Nowak" // zmieniono nazwisko na polskie
                    it[address] = "ul. Krakowska 20, 30-002 Kraków" // zmieniono adres na polski
                }
            }
            SchemaUtils.create(Orders, OrdersProducts)
        }

    }
}
