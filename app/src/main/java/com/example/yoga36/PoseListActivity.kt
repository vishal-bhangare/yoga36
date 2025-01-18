package com.example.yoga36

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class PoseListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pose_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val yogaPoses = JsonUtils.getYogaPoses(this)

        recyclerView.adapter = YogaPoseAdapter(yogaPoses) { pose ->
            val intent = Intent(this, PoseDetailActivity::class.java)
            intent.putExtra("pose", pose)
            startActivity(intent)
        }
    }
}
