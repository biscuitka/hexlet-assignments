package exercise.service;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.exception.WrongAuthorException;
import exercise.mapper.BookMapper;
import exercise.model.Author;
import exercise.model.Book;
import exercise.repository.AuthorRepository;
import exercise.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

    // BEGIN
    public List<BookDTO> getAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::map)
                .toList();
    }

    public BookDTO getById(Long id) {
        return bookMapper.map(bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found")));
    }


    public BookDTO create(BookCreateDTO createDTO) {
        authorRepository.findById(createDTO.getAuthorId())
                .orElseThrow(()->new WrongAuthorException("Author with id " + createDTO.getAuthorId() + " not found"));
        Book book = bookMapper.map(createDTO);
        return bookMapper.map(bookRepository.save(book));
    }

    public BookDTO update(Long id, BookUpdateDTO updateDTO) {
        authorRepository.findById(updateDTO.getAuthorId().get())
                .orElseThrow(()->new WrongAuthorException("Author with id " + updateDTO.getAuthorId().get() + " not found"));
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found"));
        bookMapper.update(updateDTO, book);
        return bookMapper.map(bookRepository.save(book));
    }


    public void deleteById(@PathVariable Long id) {
        bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found"));
        bookRepository.deleteById(id);
    }
//    POST /books – добавление новой книги. При указании id несуществующего автора должен вернуться ответ с кодом 400 Bad request
//    PUT /books/{id} – редактирование книги. При редактировании мы должны иметь возможность поменять название и автора.
//    При указании id несуществующего автора также должен вернуться ответ с кодом 400 Bad request
    // END
}
