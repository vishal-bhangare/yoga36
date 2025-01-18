package com.example.yoga36

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import android.net.Uri

class YogaPoseAdapter(
    private val poses: List<YogaPose>,
    private val onPoseClick: (YogaPose) -> Unit
) : RecyclerView.Adapter<YogaPoseAdapter.PoseViewHolder>() {

    class PoseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.poseImageView)
        val nameTextView: TextView = view.findViewById(R.id.poseNameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_yoga_pose, parent, false)
        return PoseViewHolder(view)
    }

    override fun onBindViewHolder(holder: PoseViewHolder, position: Int) {
        val pose = poses[position]
        holder.nameTextView.text = "${pose.sanskrit_name} (${pose.english_name})"

        GlideToVectorYou
            .init()
            .with(holder.itemView.context)
            .load(Uri.parse(pose.image), holder.imageView)

        holder.itemView.setOnClickListener { onPoseClick(pose) }
    }

    override fun getItemCount() = poses.size
}
