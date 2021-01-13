package learn.spring.boot.in.action.controller;

import learn.spring.boot.in.action.entity.BookEntity;
import learn.spring.boot.in.action.repository.BookEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "amazon")
public class BookController {

    @Setter
    private String associateId;
    @Setter
    private String productUrl;
    private final BookEntityRepository bookEntityRepository;

    @GetMapping("/{reader}")
    public String booksByReader(@PathVariable("reader") String reader, Model model) {
        List<BookEntity> readingList = bookEntityRepository.findByReader(reader);
        if (readingList != null) {
            model.addAttribute("books", readingList);
            model.addAttribute("reader", reader);
            model.addAttribute("amazonId", associateId);
            model.addAttribute("productUrl", productUrl);
        }
        return "readingList";
    }

    @PostMapping("/{reader}")
    public String addToReadingList(@PathVariable("reader") String reader, BookEntity bookEntity) {
        bookEntity.setReader(reader);
        bookEntityRepository.save(bookEntity);
        return "redirect:/{reader}";
    }

}
