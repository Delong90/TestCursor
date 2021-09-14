package com.example.testcursor

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    var userList: ListView? = null
    var header: TextView? = null
    var button: FloatingActionButton? = null
    var databaseHelper: DatabaseHelper? = null
    var db: SQLiteDatabase? = null
    var userCursor: Cursor? = null
    var userAdapter: SimpleCursorAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        header = findViewById<View>(R.id.header) as TextView
        userList = findViewById<View>(R.id.list) as ListView
        button = findViewById<View>(R.id.fab) as FloatingActionButton
        databaseHelper = DatabaseHelper(this)
        button!!.setOnClickListener{
            db!!.execSQL("INSERT INTO  ${DatabaseHelper.TABLE} (${DatabaseHelper.COLUMN_BRAND}, ${DatabaseHelper.COLUMN_MODEL}, ${DatabaseHelper.COLUMN_YEAR}, ${DatabaseHelper.COLUMN_PRICE}) VALUES ('LADA','GRANTA', 2021, 12000);")
        }
    }

    @SuppressLint("Recycle")
    public override fun onResume() {
        super.onResume()
        // открываем подключение
        db = databaseHelper!!.readableDatabase

        //получаем данные из бд в виде курсора
        userCursor = db!!.rawQuery("select * from " + DatabaseHelper.TABLE, null)
        // определяем, какие столбцы из курсора будут выводиться в ListView
        val headers = arrayOf(
            DatabaseHelper.COLUMN_BRAND,
            DatabaseHelper.COLUMN_MODEL,
            DatabaseHelper.COLUMN_YEAR,
            DatabaseHelper.COLUMN_PRICE
        )
//        val headers = arrayOf(
//            "Brand:${DatabaseHelper.COLUMN_BRAND}",
//            "Model:${DatabaseHelper.COLUMN_MODEL}",
//            "Year:${DatabaseHelper.COLUMN_YEAR}",
//            "Price:${DatabaseHelper.COLUMN_PRICE}$"
//        )

        // создаем адаптер, передаем в него курсор
        userAdapter = SimpleCursorAdapter(
            this, R.layout.line_item,
            userCursor, headers, intArrayOf(R.id.text1, R.id.text2, R.id.text3, R.id.text4), 0
        )
        header!!.text = "Найдено элементов:  + ${userCursor!!.getCount()}"
//        header!!.text = "Найдено элементов:"
        userList!!.adapter = userAdapter
    }

    public override fun onDestroy() {
        super.onDestroy()
        // Закрываем подключение и курсор
        db!!.close()
        userCursor!!.close()
    }
}