package com.lab.jpa.controller;

import com.lab.jpa.entities.Club;
import com.lab.jpa.entities.Employee;
import com.lab.jpa.repository.CompanyDao;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/emp")
public class EmpController {
    
    @Autowired
    private CompanyDao companyDao;
    
    @RequestMapping(value = {"/"}, method = {RequestMethod.GET})
    public String read(Model model) {
        
        List salary_list = companyDao.queryAllSalary();
        List club_list = companyDao.queryAllClubs();
        List dept_list = companyDao.queryAllDepts();
        List emp_list = companyDao.queryAllEmps();
        Employee emp = new Employee();
        
        model.addAttribute("emp_list", emp_list);
        model.addAttribute("club_list", club_list);
        model.addAttribute("dept_list", dept_list);
        model.addAttribute("salary_list", salary_list);
        model.addAttribute("emp", emp);
        return "emp_page";
    }
    
    
    @RequestMapping(value = {"/"}, method = {RequestMethod.POST})
    public String create(@ModelAttribute("emp") Employee emp,
                @RequestParam Integer[] clubIds) {
        
        Arrays.asList(clubIds).stream()
                .filter(e -> e != null)
                .forEach(e -> emp.getClubs().add(companyDao.getClub(e)));
        companyDao.saveEmployee(emp);
       return "redirect: ./";
    }
}
