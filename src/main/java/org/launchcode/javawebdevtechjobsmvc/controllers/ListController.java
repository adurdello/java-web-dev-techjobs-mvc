package org.launchcode.javawebdevtechjobsmvc.controllers;

import org.launchcode.javawebdevtechjobsmvc.models.Job;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.launchcode.javawebdevtechjobsmvc.models.JobData;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */


@Controller
@RequestMapping(value = "list")
public class ListController {

    //these HashMaps provide a centralized collection of the different List and Search options
    static HashMap<String, String> columnChoices = new HashMap<>();
    static HashMap<String, Object> tableChoices = new HashMap<>();

//constructor that populates columnChoices and tableChoices with values
    public ListController () {
        columnChoices.put("all", "All");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("positionType", "Position Type");
        columnChoices.put("coreCompetency", "Skill");

        tableChoices.put("employer", JobData.getAllEmployers());
        tableChoices.put("location", JobData.getAllLocations());
        tableChoices.put("positionType", JobData.getAllPositionTypes());
        tableChoices.put("coreCompetency", JobData.getAllCoreCompetency());

    }

    //handler method that renders a view with table of clickable links for job categories
    @RequestMapping(value = "")
    public String list(Model model) {
        model.addAttribute("columns", columnChoices);
        model.addAttribute("tableChoices", tableChoices);
        model.addAttribute("employers", JobData.getAllEmployers());
        model.addAttribute("locations", JobData.getAllLocations());
        model.addAttribute("positions", JobData.getAllPositionTypes());
        model.addAttribute("skills", JobData.getAllCoreCompetency());

        return "list";
    }

    //handler method that renders a view that displays info on jobs related to a category
    @RequestMapping(value = "jobs")
    public String listJobsByColumnAndValue(Model model, @RequestParam String column, @RequestParam String value) {
        ArrayList<Job> jobs;
        if (column.toLowerCase().equals("all")){
            jobs = JobData.findAll();
            model.addAttribute("title", "All Jobs");
        } else {
            jobs = JobData.findByColumnAndValue(column, value);
            model.addAttribute("title", "Jobs with " + columnChoices.get(column) + ": " + value);
        }
        model.addAttribute("jobs", jobs);
        model.addAttribute("columns", columnChoices);
        return "list-jobs";
    }
}
