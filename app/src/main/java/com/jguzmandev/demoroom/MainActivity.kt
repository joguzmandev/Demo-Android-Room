package com.jguzmandev.demoroom

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jguzmandev.demoroom.adapter.ProfessorArrayAdapter
import com.jguzmandev.demoroom.data.*
import com.jguzmandev.demoroom.databinding.ActivityMainBinding
import com.jguzmandev.demoroom.entity.Course
import com.jguzmandev.demoroom.entity.Professor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = MainActivity::class.qualifiedName

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val db: DemoRoomDatabase by lazy { DemoRoomBuilder.getInstance(this) }

    private var professor: Professor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setUpProfessorButtons()
        setUpCourseButtons()

        setUpSpinner()
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.btnLoad.id -> loadProfessor()
            binding.btnAdd.id -> addProfessor()
            binding.btnUpdate.id -> updateProfessor()
            binding.btnDelete.id -> deleteProfessor()
            binding.btnGetAll.id -> getAllProfessor()
            binding.btnLoadCourses.id->loadCoursesByProfessorId()
            binding.btnAddCourse.id -> addCourse()
            binding.btnGetAllCourse.id->getAllCourses()
        }
    }

    private fun setUpProfessorButtons() {
        with(binding) {
            btnLoad.setOnClickListener(this@MainActivity)
            btnAdd.setOnClickListener(this@MainActivity)
            btnUpdate.setOnClickListener(this@MainActivity)
            btnDelete.setOnClickListener(this@MainActivity)
            btnGetAll.setOnClickListener(this@MainActivity)
        }
    }

    private fun setUpCourseButtons() {
        with(binding) {
            btnLoadCourses.setOnClickListener(this@MainActivity)
            btnAddCourse.setOnClickListener(this@MainActivity)
            btnGetAllCourse.setOnClickListener(this@MainActivity)
        }
    }

    private fun loadProfessor() {
        val professorID = binding.etProfessorId.text.toString().toInt()
        lifecycleScope.launch(Dispatchers.IO) {
            professor = db.professorDao().findProfessorById(professorID)

            professor?.let { p ->
                with(binding) {
                    etProfessorName.text =
                        Editable.Factory.getInstance().newEditable(p.professorName)
                    etProfessorDni.text = Editable.Factory.getInstance().newEditable(p.professorDNI)
                }
            } ?: run {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Professor not found", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun addProfessor() {
        val name = binding.etProfessorName.text.toString()
        val dni = binding.etProfessorDni.text.toString()

        val professor = Professor(professorName = name, professorDNI = dni)
        lifecycleScope.launch(Dispatchers.IO) {
            db.professorDao().add(professor)
        }
        Toast.makeText(this, "Professor Saved", Toast.LENGTH_LONG).show()
        Log.d(TAG, "addProfessor: $professor")
    }

    private fun updateProfessor() {
        professor?.let { professorItem ->
            lifecycleScope.launch(Dispatchers.IO) {
                updateProfessorFromUI()
                db.professorDao().updateProfessor(professorItem)

                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@MainActivity,
                        "Professor has been updated",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    private fun deleteProfessor() {
        lifecycleScope.launch(Dispatchers.IO) {
            professor?.let {
                db.professorDao().deleteProfessor(it)
                runOnUiThread {
                    Toast.makeText(
                        this@MainActivity,
                        "Professor has been deleted success",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

    private fun getAllProfessor() {
        lifecycleScope.launch(Dispatchers.IO) {
            val professorsList = db.professorDao().getAll()
            withContext(Dispatchers.Main) {
                professorsList.forEach {
                    Log.d(TAG, "getAllProfessor: $it")
                }
            }
        }
    }

    private fun loadCoursesByProfessorId() {
       val professorId = (binding.spinnerProfessors.selectedItem as ProfessorTuple).id
        lifecycleScope.launch(Dispatchers.IO){
            val coursesList = db.courseDao().getAllCoursesByProfessorId(professorId)
            runOnUiThread {
                coursesList.forEach {
                    Log.d(TAG, "getAllCoursesByProfessorId: $it")
                }
            }
        }
    }

    private fun addCourse() {
        val _courseName = binding.etCourseName.text.toString()
        val _courseSession = binding.etCourseSession.text.toString()
        val _courseQuorum = binding.etCouseQuorum.text.toString().toInt()
        val _professorID = (binding.spinnerProfessors.selectedItem as ProfessorTuple).id

        val course = Course(
            courseName = _courseName,
            courseSession = _courseSession,
            courseQuorum = _courseQuorum,
            professor = _professorID
        )
        lifecycleScope.launch(Dispatchers.IO) {
            db.courseDao().add(course)
            runOnUiThread {
                Toast.makeText(this@MainActivity, "Course added", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun getAllCourses() {
       lifecycleScope.launch(Dispatchers.IO){
           val coursesList = db.courseDao().getAllCourses()
           runOnUiThread {
               coursesList.forEach {
                   Log.d(TAG, "getAllCourses: $it")
               }
           }
       }
    }

    private fun updateProfessorFromUI() {
        professor?.apply {
            professorName = binding.etProfessorName.text.toString()
            professorDNI = binding.etProfessorDni.text.toString()
        }
    }

    private fun setUpSpinner() {
        lifecycleScope.launch(Dispatchers.IO) {
            val professorTuple = db.professorDao().getAllProfessorNameAndIdList()
            runOnUiThread {
                val arrayAdapter = ProfessorArrayAdapter(this@MainActivity, professorTuple)
                arrayAdapter.setDropDownViewResource(R.layout.custom_spinner)
                binding.spinnerProfessors.adapter = arrayAdapter
                binding.spinnerProfessors.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            if (position != 0) {
                                val professorSelected = professorTuple[position]
                                val data = binding.spinnerProfessors.selectedItem as ProfessorTuple
                                Toast.makeText(
                                    this@MainActivity,
                                    "${professorSelected.name} - ${professorSelected.id}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {}
                    }
            }
        }


    }

}