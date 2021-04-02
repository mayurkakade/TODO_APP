package com.monusk.todo.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.monusk.todo.R
import com.monusk.todo.data.models.Priority
import com.monusk.todo.data.models.ToDoData
import com.monusk.todo.data.viewmodel.ToDoViewModel
import com.monusk.todo.databinding.FragmentUpdateBinding
import com.monusk.todo.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mToDoViewModel: ToDoViewModel by viewModels()

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater,container,false)
        binding.args = args

        //setMenu
        setHasOptionsMenu(true)

        binding.spPrioritiesUpdate.onItemSelectedListener = mSharedViewModel.listner

        return binding.root;
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_save) {
            updateItem()
        } else if(item.itemId == R.id.menu_delete) {
            confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_,_ ->
            mToDoViewModel.deleteItem(args.update)
            Toast.makeText(requireContext(),"Successfully removed : ${args.update.title}",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }

        builder.setNegativeButton("No") {_,_ ->}

        builder.setTitle("Delete ${args.update.title}?")
        builder.setMessage("Are you sure want to remove ${args.update.title}?")
        builder.create().show()
    }

    private fun updateItem() {
        val title = et_title_update.text.toString()
        val description = et_description_update.text.toString()
        val priority = sp_priorities_update.selectedItem.toString()


        if (mSharedViewModel.verifyDataFromUser(title,description)) {
            val updatedItem = ToDoData(
                args.update.id,
                title,
                mSharedViewModel.parsePriority(priority),
                description
            )
            mToDoViewModel.updateData(updatedItem)
            Toast.makeText(requireContext(), " Successfully Updated!", Toast.LENGTH_SHORT).show()
//            navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else {
            Toast.makeText(requireContext(), "Please Fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}