package ee.smit.metamudel2.controller.ui;


import ee.smit.metamudel2.model.db.DbToiminguEksemplar;
import ee.smit.metamudel2.repository.ToiminguEksemplarRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ToiminguEksemplarUiController {

    Logger logger = LoggerFactory.getLogger(ToiminguEksemplarUiController.class);

    private final ToiminguEksemplarRepository repository;

    @GetMapping("/salvestatud-toimingud")
    public String salvestatudToimingud(Model model) {
        List<DbToiminguEksemplar> dbToiminguEksemplarList = repository.findAllByOrderById();

        model.addAttribute("toiminguEksemplarList", dbToiminguEksemplarList);
        return "salvestatud-toimingud";
    }

    @GetMapping("/toimingu-redaktor")
    public String toiminguRedaktor(
            @RequestParam(name = "toimingu-eksemplar-id", required = false) UUID toiminguEksemparId,
            Model model) {

        model.addAttribute("modelToiminguEksemplarId", toiminguEksemparId);
        return "toimingu-redaktor";
    }

}

