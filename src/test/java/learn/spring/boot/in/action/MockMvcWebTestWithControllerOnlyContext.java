package learn.spring.boot.in.action;

import learn.spring.boot.in.action.config.AmazonProperties;
import learn.spring.boot.in.action.controller.BookController;
import learn.spring.boot.in.action.entity.BookEntity;
import learn.spring.boot.in.action.repository.BookEntityRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static learn.spring.boot.in.action.MockMvcWebTestWithFullContextAndNoServer.*;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = {
        BookController.class
})
public class MockMvcWebTestWithControllerOnlyContext {

    @Autowired
    ApplicationContext applicationContext;

    @MockBean
    BookEntityRepository bookEntityRepository;
    @MockBean
    AmazonProperties amazonProperties;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .forEach(System.err::println);
    }

    @Test
    void homePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/a"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("readingList"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("books"))
                .andExpect(MockMvcResultMatchers.model().attribute("books", Matchers.is(Matchers.empty())));
    }

    @Test
    void postBook() throws Exception {
        var bookEntity = new BookEntity();
        bookEntity.setId(1L);
        bookEntity.setTitle(TITLE);
        bookEntity.setAuthor(AUTHOR);
        bookEntity.setIsbn(ISBN);
        bookEntity.setDescription(DESCRIPTION);
        bookEntity.setReader(READER);

        when(bookEntityRepository.findByReader(READER)).thenReturn(List.of(bookEntity));

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/" + READER)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param(TITLE, TITLE)
                        .param(AUTHOR, AUTHOR)
                        .param(ISBN, ISBN)
                        .param(DESCRIPTION, DESCRIPTION))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/" + READER));

        mockMvc.perform(MockMvcRequestBuilders.get("/" + READER))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("readingList"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("books"))
                .andExpect(MockMvcResultMatchers.model().attribute("books", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.model().attribute("books", Matchers.contains(Matchers.samePropertyValuesAs(bookEntity))));
    }

}
