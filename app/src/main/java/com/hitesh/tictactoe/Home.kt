package com.hitesh.tictactoe


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*


class Home : Fragment(), View.OnClickListener {


    var player = true;
    var count = 0;
    private var db: FirebaseFirestore? = null
    var data: HashMap<String, String> = HashMap()


    var b = Array(3){IntArray(3)}

    lateinit var board : Array<Array<Button>>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.fragment_home, container, false)

        val button = v.findViewById<Button>(R.id.button)
        val button1 = v.findViewById<Button>(R.id.button1)
        val button2 = v.findViewById<Button>(R.id.button2)
        val button3 = v.findViewById<Button>(R.id.button3)
        val button4 = v.findViewById<Button>(R.id.button4)
        val button5 = v.findViewById<Button>(R.id.button5)
        val button6 = v.findViewById<Button>(R.id.button6)
        val button7 = v.findViewById<Button>(R.id.button7)
        val button8 = v.findViewById<Button>(R.id.button8)
        val reset = v.findViewById<Button>(R.id.rstbtn)


        board = arrayOf(
                arrayOf(button,button1,button2),
                arrayOf(button3,button4,button5),
                arrayOf(button6,button7,button8)
        )

        for (i in board){
            for (butto in i){
                butto.setOnClickListener(this)
            }
        }

        IntializeBoardStatus()

        reset.setOnClickListener{
            player = true
            count = 0
            IntializeBoardStatus()
        }

        return v
    }

    private fun IntializeBoardStatus() {


        for(i in 0..2){
            for(j in 0..2){
                b[i][j] = -1
            }
        }

        for (i in board){
            for (butto in i){
                butto.isEnabled=true
                butto.setText("")
            }
        }

    }

    override fun onClick(v: View) {

        when(v.id){
            R.id.button ->{
                updateValue(row = 0, col =0, Player = player)
                addDataToFirestore(row = 0, col =0, Player = player)
            }
            R.id.button1 ->{
                updateValue(row = 0, col =1, Player = player)
                addDataToFirestore(row = 0, col =1, Player = player)
            }
            R.id.button2 ->{
                updateValue(row = 0, col =2, Player = player)
                addDataToFirestore(row = 0, col =2, Player = player)
            }
            R.id.button3 ->{
                updateValue(row = 1, col =0, Player = player)
                addDataToFirestore(row = 1, col =0, Player = player)
            }
            R.id.button4 ->{
                updateValue(row = 1, col =1, Player = player)
                addDataToFirestore(row = 1, col =1, Player = player)
            }
            R.id.button5 ->{
                updateValue(row = 1, col =2, Player = player)
                addDataToFirestore(row = 1, col =2, Player = player)
            }
            R.id.button6 ->{
                updateValue(row = 2, col =0, Player = player)
                addDataToFirestore(row = 2, col =0, Player = player)
            }
            R.id.button7 ->{
                updateValue(row = 2, col =1, Player = player)
                addDataToFirestore(row = 2, col =1, Player = player)
            }
            R.id.button8 ->{
                updateValue(row = 2, col =2, Player = player)
                addDataToFirestore(row = 2, col =2, Player = player)
            }

        }

        player = !player
        count++
        if(player)
            updatedisplay("X's Turn")
        else
            updatedisplay("O's Turn")

        if(count==9)
            updatedisplay("Match Draw")

        checkwinner()

    }

    private fun checkwinner() {

        for(i in 0..2){
            if(b[i][0]==b[i][1] && b[i][0]==b[i][2]){
                if(b[i][0]==1)
                    status.setText("X Wins")
                else if(b[i][0]==0)
                    status.setText("O wins")
            }

            if(b[0][i]==b[1][i] && b[0][i]==b[2][i]){
                if(b[0][i]==1)
                    status.setText("X Wins")
                else if(b[0][i]==0)
                    status.setText("O wins")
            }

        }

        if(b[0][0]==b[1][1] && b[0][0]==b[2][2]){
            if(b[0][0]==1)
                status.setText("X Wins")
            else if(b[0][0]==0)
                status.setText("O wins")
        }

        if(b[0][2]==b[1][1] && b[0][2]==b[2][0]){
            if(b[0][2]==1)
                status.setText("X Wins")
            else if(b[0][2]==0)
                status.setText("O wins")
        }


    }

    private fun updatedisplay(s: String) {

        status.setText(s)

    }

    private fun updateValue(row: Int, col: Int, Player: Boolean) {

        val P = if(player) "X" else ""
        val value = if(player) 1 else 0
        board[row][col].apply {
            isEnabled = false
            setText(P)
        }
        b[row][col] = value

    }

    private fun addDataToFirestore(row: Int, col: Int, Player: Boolean) {
        db = FirebaseFirestore.getInstance()
        val dbCourses: DocumentReference = db!!.collection("room").document("0123")
        val a: String = row.toString()+col.toString()
        if(player) {
            data[a] = player.toString()
            Log.d("Working", "added data")
            dbCourses.set(data)
        }
    }
}
