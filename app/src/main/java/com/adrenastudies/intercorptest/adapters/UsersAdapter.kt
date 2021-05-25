package com.adrenastudies.intercorptest.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adrenastudies.intercorptest.R
import com.adrenastudies.intercorptest.database.User

class UsersAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var aUsers: ArrayList<User?>
    lateinit var context: Context

    class UserVH internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        var txtName:        TextView  = view.findViewById(R.id.txtName)
        var txtAge:         TextView  = view.findViewById(R.id.txtAge)
        var txtLastName:    TextView  = view.findViewById(R.id.txtLastname)
        var txtBirthdate:   TextView  = view.findViewById(R.id.txtBirthdate)
    }

    fun UsersAdapter(ctx: Context, _aUsers: ArrayList<User?>) {
        context        = ctx
        aUsers         = _aUsers
    }

    private fun setData(userVH: UserVH, position: Int) {
        val data: User? = aUsers[position]

        userVH.txtName.text      = context.resources.getString(R.string.txt_name, data!!.name)
        userVH.txtLastName.text  = context.resources.getString(R.string.txt_lastname, data.lastName)
        userVH.txtAge.text       = context.resources.getString(R.string.txt_age, data.age)
        userVH.txtBirthdate.text = context.resources.getString(R.string.txt_birthdate, data.birthdate)
    }

    private fun setComponents(holder: RecyclerView.ViewHolder, position: Int) {
        val budgetCategorysVH:UserVH = holder as UserVH
        setData(budgetCategorysVH, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.node_client, parent, false)
        return UserVH(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        setComponents(holder, position)
    }

    override fun getItemCount(): Int {
        return aUsers.size
    }
}

