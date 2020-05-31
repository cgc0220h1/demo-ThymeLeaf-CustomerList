package controllers;

import model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import service.ICustomerService;

import java.util.List;

@Controller
@RequestMapping("/")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    @GetMapping
    public ModelAndView showIndex() {
        List<Customer> customerList = customerService.findAll();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("customerList", customerList);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView create() {
        int id = customerService.findAll().size() + 1;
        Customer customer = new Customer();
        customer.setId(id);
        return new ModelAndView("create", "customer", customer);
    }

    @PostMapping("/save")
    public RedirectView save(@ModelAttribute("customer") Customer customer, RedirectAttributes redirect) {
        customerService.save(customer);
        redirect.addFlashAttribute("message", "Saved customer successfully!");
        return new RedirectView("/", true);
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("customer", customerService.findById(id));
        return modelAndView;
    }

    @PostMapping("/update")
    public RedirectView update(@ModelAttribute("customer") Customer customer, RedirectAttributes redirect) {
        customerService.update(customer.getId(), customer);
        redirect.addFlashAttribute("message", "Modified customer successfully!");
        return new RedirectView("/", true);
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable("id") int id) {
        return new ModelAndView("delete", "customer", customerService.findById(id));
    }

    @PostMapping("/delete")
    public RedirectView delete(@ModelAttribute("customer") Customer customer, RedirectAttributes redirect) {
        customerService.remove(customer.getId());
        redirect.addFlashAttribute("message", "Removed customer successfully!");
        return new RedirectView("/", true);
    }

    @GetMapping("/{id}/view")
    public ModelAndView view(@PathVariable("id") int id) {
        return new ModelAndView("view", "customer", customerService.findById(id));
    }
}
