package com.example.contactmanager.controller;

import com.example.contactmanager.model.Contact;
import com.example.contactmanager.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @GetMapping("/")
    public String home() {
        return "redirect:/add-contact";
    }

    @GetMapping("/add-contact")
    public String showAddContactForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "add-contact";
    }

    @PostMapping("/add-contact")
    public String addContact(@Valid @ModelAttribute("contact") Contact contact,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "add-contact";
        }

        try {
            contactService.saveContact(contact);
            redirectAttributes.addFlashAttribute("successMessage", "Contact ajouté avec succès");
            return "redirect:/success";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de l'ajout du contact : " + e.getMessage());
            return "redirect:/error-page";
        }
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Contact contact = contactService.getContactById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid contact Id:" + id));
        model.addAttribute("contact", contact);
        return "update-contact";
    }

    @PostMapping("/update/{id}")
    public String updateContact(@PathVariable Long id,
                                @Valid @ModelAttribute("contact") Contact contact,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            contact.setId(id);
            return "update-contact";
        }

        try {
            contactService.updateContact(contact);
            redirectAttributes.addFlashAttribute("successMessage", "Contact mis à jour avec succès");
            return "redirect:/success";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la mise à jour du contact : " + e.getMessage());
            return "redirect:/error-page";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteContact(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            contactService.deleteContact(id);
            redirectAttributes.addFlashAttribute("successMessage", "Contact supprimé avec succès");
            return "redirect:/success";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression du contact : " + e.getMessage());
            return "redirect:/error-page";
        }
    }

    @GetMapping("/success")
    public String successPage() {
        return "success";
    }

    @GetMapping("/error-page")
    public String errorPage() {
        return "error-page";
    }

    @GetMapping("/all-contacts")
    public String listContacts(Model model) {
        model.addAttribute("contacts", contactService.getAllContacts());
        return "contacts";
    }
    @GetMapping("/search")
    public String searchContacts(@RequestParam(required = false) String firstName, Model model) {
        model.addAttribute("contacts", contactService.searchByFirstName(firstName));
        return "contacts";
    }
}
