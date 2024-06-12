package exercise.service;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.model.Author;
import exercise.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    // BEGIN
    public List<AuthorDTO> getAll(){
        return authorRepository.findAll().stream()
                .map(authorMapper::map)
                .toList();
    }

    public AuthorDTO getById(Long id){
        return authorMapper.map(authorRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Author with id " + id + " not found")));
    }

    public AuthorDTO create(AuthorCreateDTO createDTO){
        Author author = authorMapper.map(createDTO);
        return authorMapper.map(authorRepository.save(author));
    }


    public AuthorDTO update(Long id, AuthorUpdateDTO updateDTO){
        Author author = authorRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Author with id " + id + " not found"));
        authorMapper.update(updateDTO, author);
        return authorMapper.map(authorRepository.save(author));
    }


    public void deleteById(Long id) {
        authorRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Author with id " + id + " not found"));
        authorRepository.deleteById(id);
    }
    // END
}
