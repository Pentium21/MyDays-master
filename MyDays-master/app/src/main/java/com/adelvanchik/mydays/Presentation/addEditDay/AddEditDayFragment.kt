package com.adelvanchik.mydays.Presentation.addEditDay

import android.app.AlertDialog
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.adelvanchik.mydays.Domain.entity.Day
import com.adelvanchik.mydays.Presentation.MenuFragment
import com.adelvanchik.mydays.R
import com.adelvanchik.mydays.databinding.FragmentAddEditDayBinding


class AddEditDayFragment : Fragment() {

    private val vm by lazy { ViewModelProvider(requireActivity())[AddEditDayViewModel::class.java] }

    private var _binding: FragmentAddEditDayBinding? = null
    private val binding: FragmentAddEditDayBinding
        get() = _binding ?: throw RuntimeException("FragmentAddEditDayBinding == null")

    private lateinit var day: Day
    private var countCupWithWater = 0

    private lateinit var buttonWaterList: List<ImageButton>
    private lateinit var buttonsWaterIsFullArray: Array<Boolean>
    private lateinit var buttonSleepList: Array<Button>
    private lateinit var buttonPlannedTasksList: Array<Button>
    private lateinit var buttonCompletedTasksList: Array<Button>
    private lateinit var buttonHealthList: List<ImageButton>
    var choiceHealth = HEALTH_NOT_SELECTED

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddEditDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseArgs()
        vm.initialDay(day)
        dayObserver()
        // В каждом методе, где есть listener вызывается метод parse, для нахождения значения
        // в базе данных
        buttonsHealthListener()
        buttonsSleepListener()
        buttonsSleepArrowListener()
        buttonsTasksArrowListener()
        buttonsResultTasksArrowListener()
        buttonsWaterListener()
        buttonsTasksListener()
        achievementsEditTextListener()
        thoughtsEditTextListener()
        buttonBackCLickListener()
        buttonDoneClickListener()

        binding.btnDeleteDay.setOnClickListener {
            showDeleteMessage(day)
        }
    }

    private fun buttonDoneClickListener() {
        binding.btnDoneEditDay.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack(MenuFragment.getNameBackStack(),
                1)
        }
    }

    private fun buttonBackCLickListener() {
        binding.btnBackInEditDay.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }


    private fun buttonsHealthListener() {
        buttonHealthList = listOf(
            binding.btnHealth1,
            binding.btnHealth2,
            binding.btnHealth3,
            binding.btnHealth4,
            binding.btnHealth5,
        )

        parseHealth()

        for (i in buttonHealthList.indices) {
            buttonHealthList[i].setOnClickListener {
                if (choiceHealth != HEALTH_NOT_SELECTED) {
                    animateViewScale(buttonHealthList[choiceHealth],
                        SCALE_DEFAULT)
                }
                if (choiceHealth != i) {
                    changeHealthsOnActive(i)
                } else {
                    animateViewScale(buttonHealthList[i], SCALE_DEFAULT)
                    vm.addEditDay(day.copy(health = Day.DEFAULT_SHORT_VALUE))
                    choiceHealth = HEALTH_NOT_SELECTED
                }
            }
        }
    }

    private fun changeHealthsOnActive(i: Int) {
        animateViewScale(buttonHealthList[i], SCALE_WHEN_HEALTH_IS_SELECTED)
        choiceHealth = i
        vm.addEditDay(day.copy(health = i.toShort()))
    }

    private fun parseHealth() {
        if (day.health != Day.DEFAULT_SHORT_VALUE) {
            val index = day.health.toInt()
            changeHealthsOnActive(index)
        }
    }

    private fun animateViewScale(view: View, valueScale: Float) {
        view.animate()
            .scaleY(valueScale)
            .scaleX(valueScale)
            .duration = 200
    }

    private fun animateViewScaleAndReturnScale(view: View, valueScale: Float) {
        view.animate()
            .scaleY(valueScale)
            .scaleX(valueScale).duration = 300
        view.animate()
            .scaleY(SCALE_DEFAULT)
            .scaleX(SCALE_DEFAULT).duration = 100
    }


    private fun buttonsSleepListener() {
        buttonSleepList = arrayOf(
            binding.btnSleep0,
            binding.btnSleep1,
            binding.btnSleep2,
            binding.btnSleep3,
            binding.btnSleep4,
            binding.btnSleep5,
            binding.btnSleep6,
            binding.btnSleep7,
            binding.btnSleep8,
            binding.btnSleep9,
            binding.btnSleep10,
            binding.btnSleep11,
            binding.btnSleep12,
        )

        parseSleepActiveButton()

        for (i in buttonSleepList.indices) {
            buttonSleepList[i].setOnClickListener {
                if (!day.durationOfSleep.equals(i.toShort())) {
                    vm.addEditDay(day.copy(durationOfSleep = i.toShort()))
                } else {
                    vm.addEditDay(day.copy(durationOfSleep = Day.DEFAULT_SHORT_VALUE))
                }
            }
        }
    }

    private fun parseSleepActiveButton() {
        if (day.durationOfSleep != Day.DEFAULT_SHORT_VALUE) {
            val index = day.durationOfSleep.toInt()
            buttonSleepList[index].performClick()
        }
    }

    private fun buttonsSleepArrowListener() {
        binding.btnSleepArrowLeft.setOnClickListener {
            binding.scrollViewSleep.scrollBy(MOTION_LEFT_ARROW_SLEEP_BUTTONS, 0)
        }
        binding.btnSleepArrowRight.setOnClickListener {
            animateViewScaleAndReturnScale(it, SCALE_WHEN_USER_CLICK_ARROW_BUTTON)
            binding.scrollViewSleep.scrollBy(MOTION_RIGHT_ARROW_SLEEP_BUTTONS, 0)
        }
    }

    private fun buttonsTasksArrowListener() {
        binding.btnTasksArrowLeft.setOnClickListener {
            binding.scrollTask.scrollBy(MOTION_LEFT_ARROW_TASKS_BUTTONS, 0)
        }
        binding.btnTasksArrowRight.setOnClickListener {
            animateViewScaleAndReturnScale(it, SCALE_WHEN_USER_CLICK_ARROW_BUTTON)
            binding.scrollTask.scrollBy(MOTION_RIGHT_ARROW_TASKS_BUTTONS, 0)
        }
    }

    private fun buttonsResultTasksArrowListener() {
        binding.btnResultTasksArrowLeft.setOnClickListener {
            binding.scrollResultTask.scrollBy(MOTION_LEFT_ARROW_TASKS_BUTTONS, 0)
        }
        binding.btnResultTasksArrowRight.setOnClickListener {
            animateViewScaleAndReturnScale(it, SCALE_WHEN_USER_CLICK_ARROW_BUTTON)
            binding.scrollResultTask.scrollBy(MOTION_RIGHT_ARROW_TASKS_BUTTONS, 0)
        }
    }

    private fun buttonsTasksListener() {
        buttonPlannedTasksList = arrayOf(
            binding.btnCountTasks1,
            binding.btnCountTasks2,
            binding.btnCountTasks3,
            binding.btnCountTasks4,
            binding.btnCountTasks5,
            binding.btnCountTasks6,
            binding.btnCountTasks7,
            binding.btnCountTasks8,
            binding.btnCountTasks9,
        )

        buttonCompletedTasksList = arrayOf(
            binding.btnCountTasksResult0,
            binding.btnCountTasksResult1,
            binding.btnCountTasksResult2,
            binding.btnCountTasksResult3,
            binding.btnCountTasksResult4,
            binding.btnCountTasksResult5,
            binding.btnCountTasksResult6,
            binding.btnCountTasksResult7,
            binding.btnCountTasksResult8,
            binding.btnCountTasksResult9,
        )

        parseTasksPlannedAndCompletedActiveButton()

        for (i in buttonPlannedTasksList.indices) {
            buttonPlannedTasksList[i].setOnClickListener {
                if (!day.countOfPlannedTasks.equals((i + 1).toShort())) {
                    // Поскольку индексация в листе начинается с 0, а значение в данной
                    // ячейке равно 1, то увеличиваем значение индекса на 1
                    if (day.countOfCompletedTasks > i + 1) {
                        binding.toggleCountTasksResultList.clearChecked()
                        vm.addEditDay(day.copy(
                            countOfPlannedTasks = (i + 1).toShort(),
                            countOfCompletedTasks = Day.DEFAULT_SHORT_VALUE,
                            percOfCompletedTasks = Day.DEFAULT_SHORT_VALUE,
                        ))
                    } else {
                        val countCompletedTasks = day.countOfCompletedTasks
                        if (countCompletedTasks == Day.DEFAULT_SHORT_VALUE) {
                            vm.addEditDay(day.copy(countOfPlannedTasks = (i + 1).toShort()))
                        } else {
                            vm.addEditDay(day.copy(
                                countOfPlannedTasks = (i + 1).toShort(),
                                percOfCompletedTasks = calculatePercentOfCompletedTasks(
                                    plannedTasks = (i+1).toShort(),
                                    completedTasks = countCompletedTasks
                                )
                            ))
                        }
                    }
                } else {
                    vm.addEditDay(day.copy(
                        countOfPlannedTasks = Day.DEFAULT_SHORT_VALUE,
                        countOfCompletedTasks = Day.DEFAULT_SHORT_VALUE,
                        percOfCompletedTasks = Day.DEFAULT_SHORT_VALUE,
                    ))
                    binding.toggleCountTasksResultList.clearChecked()
                }
            }
        }

        for (i in buttonCompletedTasksList.indices) {
            buttonCompletedTasksList[i].setOnClickListener {
                if (!day.countOfCompletedTasks.equals(i.toFloat())) {
                    val countPlannedTasks = day.countOfPlannedTasks
                    if (countPlannedTasks == Day.DEFAULT_SHORT_VALUE) {
                        showToast(resources.getString(R.string.firstlySelectCountOfPlannedTasks))
                        binding.toggleCountTasksResultList.clearChecked()
                    }
                    if (countPlannedTasks < i) {
                        showToast(resources.getString(R.string.incorrectCountOfComletedTasks))
                        binding.toggleCountTasksResultList.clearChecked()
                    } else {
                        vm.addEditDay(day.copy(
                            countOfCompletedTasks = i.toShort(),
                            percOfCompletedTasks = calculatePercentOfCompletedTasks(
                                plannedTasks = countPlannedTasks,
                                completedTasks = i.toShort()
                                )))
                    }
                } else {
                    vm.addEditDay(day.copy(
                        countOfCompletedTasks = Day.DEFAULT_SHORT_VALUE,
                        percOfCompletedTasks = Day.DEFAULT_SHORT_VALUE,
                    ))
                }
            }
        }
    }

    private fun calculatePercentOfCompletedTasks(plannedTasks: Short, completedTasks: Short): Short {
        return (completedTasks * 100 / plannedTasks).toShort()
    }

    private fun parseTasksPlannedAndCompletedActiveButton() {
        if (day.countOfPlannedTasks != Day.DEFAULT_SHORT_VALUE) {
            // Значение количества запланированных задач на 1 больше, чем индек, поэтому уменьшаем
            // на 1
            val indexForPlannedTasks = day.countOfPlannedTasks.toInt() - 1
            buttonPlannedTasksList[indexForPlannedTasks].performClick()
        }
        if (day.countOfCompletedTasks != Day.DEFAULT_SHORT_VALUE) {
            val indexForCompletedTasks = day.countOfCompletedTasks.toInt()
            buttonCompletedTasksList[indexForCompletedTasks].performClick()
        }
    }

    private fun buttonsWaterListener() {

        buttonWaterList = listOf(
            binding.btnCup1,
            binding.btnCup2,
            binding.btnCup3,
            binding.btnCup4,
            binding.btnCup5,
            binding.btnCup6,
            binding.btnCup7,
            binding.btnCup8,
            binding.btnCup9,
            binding.btnCup10,
            binding.btnCup11,
            binding.btnCup12,
        )

        buttonsWaterIsFullArray = Array(buttonWaterList.size) { false }

        parseCupWithWater()

        val musicWhenUserButtonToCup = MediaPlayer.create(requireContext(), R.raw.chpuk)

        for (i in buttonWaterList.indices) {
            buttonWaterList[i].setOnClickListener {
                changeCup(buttonWaterList[i], buttonsWaterIsFullArray[i])
                musicWhenUserButtonToCup.start()
                buttonsWaterIsFullArray[i] = !buttonsWaterIsFullArray[i]
            }
        }
    }

    private fun parseCupWithWater() {
        if (day.amountOfWater != Day.DEFAULT_FLOAT_VALUE) {
            val countCup = (day.amountOfWater / 0.25f).toInt()
            for (i in 0 until countCup) {
                changeCup(buttonWaterList[i], buttonsWaterIsFullArray[i])
                buttonsWaterIsFullArray[i] = !buttonsWaterIsFullArray[i]
            }
        }
    }

    private fun changeCup(imageButton: ImageButton, isFullCup: Boolean) {
        var image: Int = 0
        if (isFullCup) {
            animateViewScale(imageButton, SCALE_DEFAULT)
            image = R.drawable.ic_cup_empty
            countCupWithWater--
        } else {
            animateViewScale(imageButton, SCALE_WHEN_CUP_IS_SELECTED)
            image = R.drawable.ic_cup_full
            countCupWithWater++
        }

        val countWater = countCupWithWater * SIZE_ONE_CUP
        waterInfoLoad(countWater)
        imageButton.setImageResource(image)
    }

    private fun waterInfoLoad(countWater: Float) {
        if (countWater == 0f) {
            binding.sumCountWater.text = EMPTY_STRING
            vm.addEditDay(day.copy(amountOfWater = Day.DEFAULT_FLOAT_VALUE))
        } else {
            binding.sumCountWater.text = "$countWater ${resources.getText(R.string.liter)}"
            vm.addEditDay(day.copy(amountOfWater = countWater))
        }
    }

    private fun dayObserver() {
        vm.myDay.observe(viewLifecycleOwner) {
            day = it
        }
    }

    private fun achievementsEditTextListener() {
        binding.etAchievements.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int,
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                vm.addEditDay(day.copy(achievements = s.toString()))
            }

        })
    }

    private fun thoughtsEditTextListener() {
        binding.etThoughts.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int,
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                vm.addEditDay(day.copy(thoughts = s.toString()))
            }

        })
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDeleteMessage(day: Day) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Удалить?")
            .setPositiveButton("Да") { _, _ ->
                vm.deleteDay(day)
                requireActivity().supportFragmentManager.popBackStack()
            }
            .setNegativeButton("Нет") { _, _ ->

            }.create().show()
    }


    @Suppress("DEPRECATION")
    fun parseArgs() {
        requireArguments().getParcelable<Day>(DAY_KEY)?.let {
            day = it
            if (day.achievements != Day.DEFAULT_STRING_VALUE) {
                binding.etAchievements.setText(it.achievements)
            }
            if (day.thoughts != Day.DEFAULT_STRING_VALUE) {
                binding.etThoughts.setText(day.thoughts)
            }
            binding.tvData.text = day.dataStringFormat
        }
    }

    companion object {
        private const val DAY_KEY: String = "day_key"
        private const val HEALTH_NOT_SELECTED = -1
        private const val SCALE_WHEN_HEALTH_IS_SELECTED = 1.2f
        private const val SCALE_WHEN_CUP_IS_SELECTED = 1.05f
        private const val SCALE_WHEN_USER_CLICK_ARROW_BUTTON = 1.2f
        private const val MOTION_RIGHT_ARROW_SLEEP_BUTTONS = 360
        private const val MOTION_RIGHT_ARROW_TASKS_BUTTONS = 120
        private const val MOTION_LEFT_ARROW_SLEEP_BUTTONS = -360
        private const val MOTION_LEFT_ARROW_TASKS_BUTTONS = -120
        private const val SIZE_ONE_CUP = 0.25f
        private const val SCALE_DEFAULT = 1f
        private const val EMPTY_STRING = ""

        fun newInstance(day: Day): AddEditDayFragment {
            return AddEditDayFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(DAY_KEY, day)
                }
            }
        }
    }

}