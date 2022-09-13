package nl.argo360.crm.controller;

import nl.argo360.crm.doa.Account;
import nl.argo360.crm.repo.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CrmController {

    AccountRepository crmRepository;

    Logger log = LoggerFactory.getLogger(CrmController.class);

    public CrmController(AccountRepository flightSearchRepository) {
        this.crmRepository = crmRepository;
    }

    @GetMapping("/companyName")
    public List<Account> getAllAccounts(@RequestParam(required = false) Sort sortField) {
        return crmRepository.findAll(sortField);
    }
}
