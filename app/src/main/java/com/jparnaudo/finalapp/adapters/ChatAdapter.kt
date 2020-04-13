package com.jparnaudo.finalapp.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jparnaudo.finalapp.R
import com.jparnaudo.finalapp.inflate
import com.jparnaudo.finalapp.models.Message
import com.jparnaudo.finalapp.utils.CircleTransform
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_chat_item_left.view.*
import kotlinx.android.synthetic.main.fragment_chat_item_right.view.*
import java.text.SimpleDateFormat

class ChatAdapter(val items: List<Message>, val userId: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val GLOBAL_MESSAGE = 1
    private val MY_MESSAGE = 2

    //    es la representación del layout de la derecha e izq.
    private val layoutRight = R.layout.fragment_chat_item_right
    private val layoutLeft = R.layout.fragment_chat_item_left

    override fun getItemViewType(position: Int) =
        if (items[position].authorId == userId) MY_MESSAGE else GLOBAL_MESSAGE

    override fun getItemCount() = items.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MY_MESSAGE -> ViewHolderR(parent.inflate(layoutRight))
            else -> ViewHolderL(parent.inflate(layoutLeft))
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            MY_MESSAGE ->(holder as ViewHolderR).bind(items[position])
            GLOBAL_MESSAGE -> (holder as ViewHolderL).bind(items[position])
        }
    }

    //de esta forma se crea un ViewHolder
    class ViewHolderR(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: Message) = with(itemView) {
            textViewMessageRight.text = message.message
            textViewTimeright.text = SimpleDateFormat("hh:mm").format(message.sentAT)
//    Picasso load image here --------------------
//            carga la imagen y la redondea
        Picasso.get().load(message.profileImageURL).resize(100,100)
            .centerCrop().transform(CircleTransform()).into(imageViewProfileRight)

        }
    }

    class ViewHolderL(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: Message) = with(itemView) {
            textViewMessageLeft.text = message.message
            textViewTimeLeft.text = SimpleDateFormat("hh:mm").format(message.sentAT)
//    Picasso load image here --------------------
            Picasso.get().load(message.profileImageURL).resize(100,100)
                .centerCrop().transform(CircleTransform()).into(imageViewProfileLeft)


        }

    }
}