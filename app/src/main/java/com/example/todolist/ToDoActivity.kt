package com.example.todolist

import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.io.File
import java.io.PrintStream
import java.util.*
import kotlin.collections.ArrayList

class ToDoActivity : AppCompatActivity() {
    var itemlist = arrayListOf<String>()

    var  adapter : ArrayAdapter<String>?=null
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, itemlist)
        var  obje:String?=null

        for (position in itemlist.indices){
            obje= itemlist.get(position)
        }
         show.setOnClickListener{

             readList()

         }


        add.setOnClickListener {

            if(editText.text.toString().isNotEmpty()){

                itemlist.add(editText.text.toString())
                listView.adapter =  adapter
                adapter!!.notifyDataSetChanged()
                // This is because every time when you add the item the input      space or the eidt text space will be cleared
                editText.text.clear()



                for (position in itemlist.indices){
                    obje= itemlist.get(position)
                }
                saveList1(obje)
                for (position in itemlist.indices){
                    obje= itemlist.get(position)
                }

            }else{
                android.widget.Toast.makeText(this, "Escrita algo para agregar a la lista", android.widget.Toast.LENGTH_SHORT).show()
            }


            //readList()

        }


        delete.setOnClickListener {


            val position: SparseBooleanArray = listView.checkedItemPositions
            val count = listView.count
            var item = count - 1
            while (item >= 0) {
                if (position.get(item))
                {
                    adapter!!.remove(itemlist.get(item))


                }
                item--
            }
            adapter!!.notifyDataSetChanged()


        }

        clear.setOnClickListener {

            itemlist.clear()
            adapter!!.notifyDataSetChanged()

            deleeteFile()

        }
        listView.setOnItemClickListener { adapterView, view, i, l ->
            android.widget.Toast.makeText(this, "You Selected the item --> "+itemlist.get(i), android.widget.Toast.LENGTH_SHORT).show()
        }





    }

    fun saveList1(obje: String?){

        if (obje!=null) {
            val out = PrintStream(openFileOutput("lista2.txt", Activity.MODE_APPEND))
            out.println(obje)
            out.close()
        }

    }

    fun readList(){

        var file : File
        file= getFileStreamPath("lista2.txt")
        if (file.exists()){
            itemlist.clear()
            val input =Scanner(openFileInput("lista2.txt"))

            while (input.hasNextLine()){
                val l=input.nextLine()
                itemlist.add(l)
            }
            input.close()

            listView.adapter =  adapter
            adapter!!.notifyDataSetChanged()

        }else
        {
            android.widget.Toast.makeText(this, "No hay lista para ver", android.widget.Toast.LENGTH_SHORT).show()
        }
      }


fun deleeteFile(){
    var file : File
    file= getFileStreamPath("lista2.txt")
    if (file.exists()){
        deleteFile("lista2.txt")
        adapter!!.notifyDataSetChanged()
    }

}

}