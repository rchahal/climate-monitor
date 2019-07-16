package com.expbank.climatemonitor;

import com.expbank.climatemonitor.util.CsvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/climateInfo/")
public class ClimateInfoController {

    Logger logger = LogManager.getLogger(ClimateInfoController.class);

    private final ClimateInfoRepository climateInfoRepository;

    @Autowired
    public ClimateInfoController(ClimateInfoRepository climateInfoRepository){
        this.climateInfoRepository = climateInfoRepository;
    }

    @GetMapping("list")
    public String listClimateInfo(Model model) {
        model.addAttribute("climateInfoTable", climateInfoRepository.findAll());
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
}
