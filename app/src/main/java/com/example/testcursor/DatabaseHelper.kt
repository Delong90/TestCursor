package com.example.testcursor

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, SCHEMA) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLE ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_BRAND TEXT, $COLUMN_MODEL TEXT, $COLUMN_YEAR INTEGER,$COLUMN_PRICE INTEGER);"
        )

        // добавление начальных данных

        db.execSQL("INSERT INTO  $TABLE ($COLUMN_BRAND, $COLUMN_MODEL, $COLUMN_YEAR, $COLUMN_PRICE) VALUES ('VW','POLO', 2000, 1500);")
        db.execSQL("INSERT INTO  $TABLE ($COLUMN_BRAND, $COLUMN_MODEL, $COLUMN_YEAR, $COLUMN_PRICE) VALUES ('Mercedes','C100', 2005,5000);")



    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE")
        onCreate(db)
    }

//

    companion object {
        private val DATABASE_NAME = "cars-database" // название бд
        private val SCHEMA = 1 // версия базы данных
        val TABLE = "cars" // название таблицы в бд

        // названия столбцов
        val COLUMN_ID = "_id"
        val COLUMN_BRAND = "brand"
        val COLUMN_MODEL = "model"
        val COLUMN_YEAR = "year"
        val COLUMN_PRICE = "price"
    }
}