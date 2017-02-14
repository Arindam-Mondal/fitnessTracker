package com.pluralsight.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pluralsight.model.Activity;
import com.pluralsight.model.Exercise;
import com.pluralsight.model.Goal;
import com.pluralsight.service.ExerciseService;
import com.pluralsight.service.GoalService;


@Controller
public class MinutesController {

	@Autowired
	private ExerciseService exerciseService;
	
	@Autowired
	private GoalService goalService;
	
	@RequestMapping(value = "/addMinutes",  method = RequestMethod.GET)
	public String getMinutes(@ModelAttribute ("exercise") Exercise exercise) {
	
		return "addMinutes";
	}
	
	@RequestMapping(value = "/addMinutes",  method = RequestMethod.POST)
	public String addMinutes(@Valid @ModelAttribute ("exercise") Exercise exercise, BindingResult result,HttpSession session) {
		
		System.out.println("exercise: " + exercise.getMinutes());
		System.out.println("exercise activity: " + exercise.getActivity());
		
		if(result.hasErrors()) {
			return "addMinutes";
		}else{
			Goal goal = (Goal)session.getAttribute("goal");
			exercise.setGoal(goal);
			exerciseService.save(exercise);
		}
		
		logAllGoals();
		
		return "addMinutes";
	}
	

	@RequestMapping(value = "/activities", method = RequestMethod.GET)
	public @ResponseBody List<Activity> findAllActivities() {
		return exerciseService.findAllActivities();
	}
	
	private void logAllGoals() {
		
		System.out.println("Inside MinutesController");
		
		List<Goal> goalList = goalService.loadAll();
		for(Goal g:goalList){
			System.out.println(g.getId()+" "+g.getMinutes());
			List<Exercise> exerciseList = g.getExercises();
			for(Exercise e:exerciseList){
				System.out.println("\t"+e.getId()+" "+e.getMinutes());
			}
			
		}
	}
}
