package com.example.contactmanager.service;

import com.example.contactmanager.model.Contact;
import com.example.contactmanager.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ContactService {
    private final ContactRepository contactRepository;

    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }

    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }

    public Contact updateContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public List<Contact> searchByFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            return getAllContacts();
        }
        return contactRepository.findByFirstNameContainingIgnoreCase(firstName.trim());
    }
}
