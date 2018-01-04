package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", ListController.columnChoices); //columnChoices is a HashMap
        // The keys, such as "employer," are used in search.html as th input ids and values
        // The values, such as "Employer", are used as th label text
        // The values are "Skill", "Employer", "Location", "Position Type", and "All"
        // The key for "Skill" is "core competency"
        // columnChoices is defined with key-value pairs enumerated in ListController itself, not JobData or the CSV
        return "search";
    }

    // TODO #1 - Create handler to process search request and display results

    @RequestMapping(value = "results")
    public String results(String searchType, String searchTerm, Model model) {
        // In JobData, findByColumnAndValue(String column, String value) returns an ArrayList<HashMap<String, String>>
        // A Java HashMap is roughly equivalent to a Python dictionary
        // It fetches data from the private static ArrayList<HashMap<String, String>> allJobs
        // allJobs is populated using loadData to get data from the CSV
        // Each HashMap within the ArrayList is one job
        // Within a HashMap, each key-value pair is a mapping from a column label like "core competency" ...
                // ... to the entry for that job
        // findByValue also returns an ArrayList<HashMap<String, String>>
        // The column labels in the CSV are name, employer, location, position type, core competency
        // These are the same column choices in the search type, but with "name" present and no "all" column.
        // We can use findByValue if the searchType is "all" and findByColumnAndValue otherwise
        // findByValue takes a single String, value, as an input.
        // JobData's findByColumnAndValue takes a String column and then a String value as inputs.
        // For "core competency", "employer", "location", and "position type" search types chosen via radio button ...
                // ... We can input a column String into  JobData's findByColumnAndValue ...
                // ... Which matches the name of the search type exactly, since they match the CSV imports in allJobs
        // Our first TODO is to create a handler that gets the search results and passes them to search.html ...
                // ... through the th model, while the second is to display these results in search.html.
        // The second TODO will require search.html to iterate over what the first TODO passed in, using a loop
        // If it can be iterated over, it would make sense to pass in an ArrayList<HashMap<String, String>> ...
                // ... since this is what findByValue returns and contains the data of the returned jobs. ...
                // ... We could name this arrayList "someJobs" or "resultJobs".
        // We know that search.html already iterates over the columns (key-value pairs) ...
                // of the columnChoices HashMap from ListController passed in as "columns"
        if (searchType.equals("all")) {
            boolean allSearch = true;
            model.addAttribute("allSearchStatus", allSearch);
            ArrayList<HashMap<String, String>> results = JobData.findByValue(searchTerm);
            model.addAttribute("resultJobsArrayList", results);
            model.addAttribute("resultCount", results.size());
        }
        else if (searchType.equals("core competency") || searchType.equals("employer") || searchType.equals("location")
                                                                    || searchType.equals("position type")) {
            boolean specificColumn = true;
            model.addAttribute("specificStatus", specificColumn);
            ArrayList<HashMap<String, String>> results = JobData.findByColumnAndValue(searchType, searchTerm);
            model.addAttribute("resultJobsArrayList", results);
            model.addAttribute("resultCount", results.size());
        }
        else {
            boolean problem = true;
            model.addAttribute("problemStatus", problem);
        }

        model.addAttribute("columns", ListController.columnChoices); //columnChoices is a HashMap
        return "search";
    }


}
