package exercise.dto;

import exercise.model.Contact;

public class ContactMapper {

    public static Contact toEntity(ContactCreateDTO createDTO){
        Contact contact = new Contact();
        contact.setPhone(createDTO.getPhone());
        contact.setFirstName(createDTO.getFirstName());
        contact.setLastName(createDTO.getLastName());
        return contact;
    }

    public static ContactDTO toDto(Contact contact){
        ContactDTO dto = new ContactDTO();
        dto.setId(contact.getId());
        dto.setPhone(contact.getPhone());
        dto.setFirstName(contact.getFirstName());
        dto.setLastName(contact.getLastName());
        dto.setCreatedAt(contact.getCreatedAt());
        dto.setUpdatedAt(contact.getUpdatedAt());
        return dto;
    }
}
