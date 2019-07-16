package com.expbank.climatemonitor;

import com.expbank.climatemonitor.util.CsvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Controller
@RequestMapping("/climateInfo/")
public class ClimateInfoController {

    private Logger logger = LogManager.getLogger(ClimateInfoController.class);

    private final ClimateInfoRepository climateInfoRepository;

    @Autowired
    public ClimateInfoController(ClimateInfoRepository climateInfoRepository){
        this.climateInfoRepository = climateInfoRepository;
    }

    @GetMapping("list")
    public String listClimateInfo(Model model) {
        model.addAttribute("climateInfoTable", climateInfoRepository.findAll());
        model.addAttribute("dateFilter", new DateFilter());
        return "index";
    }

    @PostMapping("filteredList")
    public String filteredClimateInfo(@ModelAttribute DateFilter dateFilter, Model model) {

        Date startDate=null;
        Date endDate=null;

        try {
            if (dateFilter.getStartDate()!=null && dateFilter.getStartDate().length()>0) {
                startDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateFilter.getStartDate());
            }
        } catch (Exception e) {
            // TODO:  Display validation error for Start Date
        }

        try {
            if (dateFilter.getEndDate()!=null && dateFilter.getEndDate().length()>0) {
                endDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateFilter.getEndDate());
            }
        } catch (Exception e) {
            // TODO:  Display validation error for End Date
        }

        // TODO:  There has to be a way to do this with a single method but I'm out of time.

        if (startDate==null && endDate==null) {
            model.addAttribute("climateInfoTable", climateInfoRepository.findAll());
        } else if (startDate==null) {
            model.addAttribute("climateInfoTable", climateInfoRepository.findBeforeEndDate(endDate));
        } else if (endDate==null) {
            model.addAttribute("climateInfoTable", climateInfoRepository.findAfterStartDate(startDate));
        } else {
            if (startDate.after(endDate)) {
                // TODO:  Display a validation error
            } else {
                model.addAttribute("climateInfoTable", climateInfoRepository.findByDateRange(startDate, endDate));
            }
        }

        model.addAttribute("dateFilter", dateFilter);

        return "index";
    }

    @GetMapping("loadAllData")
    public String loadAllData(Model model) {
        try {
            climateInfoRepository.deleteAll();
            File file = ResourceUtils.getFile("classpath:eng-climate-summary.csv");
            List<ClimateInfo> climateInfoList = CsvUtils.read(ClimateInfo.class, new FileInputStream(file));
            climateInfoRepository.saveAll(climateInfoList);
        }catch(IOException ioe){
            logger.error("File 'eng-climate-summary.csv' load error.", ioe);
            throw new RuntimeException(ioe);
        }
        model.addAttribute("climateInfoTable", climateInfoRepository.findAll());
        model.addAttribute("dateFilter", new DateFilter());

        return "redirect:list";
    }

    @GetMapping("detail/{id}")
    public String detail(@PathVariable("id") long id, Model model) {
        ClimateInfo climateInfo;
        climateInfo = climateInfoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        List<ClimateInfo> climateInfoList = new ArrayList<>();
        climateInfoList.add(climateInfo);
        model.addAttribute("climateInfoTable", climateInfoList);
        return "detail";
    }

    @GetMapping("error")
    public String error() {

        return "error";
    }
}
