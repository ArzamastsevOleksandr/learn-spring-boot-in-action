package learn.spring.boot.in.action.controller;

import learn.spring.boot.in.action.config.AmazonProperties;
import learn.spring.boot.in.action.entity.BookEntity;
import learn.spring.boot.in.action.repository.BookEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookEntityRepository bookEntityRepository;
    private final AmazonProperties amazonProperties;

    @GetMapping("/{reader}")
    public String booksByReader(@PathVariable("reader") String reader, Model model) {
        List<BookEntity> readingList = bookEntityRepository.findByReader(reader);
        if (readingList != null) {
            model.addAttribute("books", readingList);
            model.addAttribute("reader", reader);
            model.addAttribute("amazonId", amazonProperties.getAssociateId());
            model.addAttribute("productUrl", amazonProperties.getProductUrl());
        }
        return "readingList";
    }

    @PostMapping("/{reader}")
    public String addToReadingList(@PathVariable("reader") String reader, BookEntity bookEntity) {
        bookEntity.setReader(reader);
        bookEntityRepository.save(bookEntity);
        return "redirect:/books/{reader}";
    }

}
