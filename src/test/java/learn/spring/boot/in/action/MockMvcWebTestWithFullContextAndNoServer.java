package learn.spring.boot.in.action;

import learn.spring.boot.in.action.entity.BookEntity;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc // All the context is loaded but the server is not started
public class MockMvcWebTestWithFullContextAndNoServer {

    static final String TITLE = "title";
    static final String ISBN = "isbn";
    static final String AUTHOR = "author";
    static final String DESCRIPTION = "description";
    static final String READER = "reader";

    @Autowired
    ApplicationContext applicationContext;

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

        var bookEntity = new BookEntity();
        bookEntity.setId(1L);
        bookEntity.setTitle(TITLE);
        bookEntity.setAuthor(AUTHOR);
        bookEntity.setIsbn(ISBN);
        bookEntity.setDescription(DESCRIPTION);
        bookEntity.setReader(READER);

        mockMvc.perform(MockMvcRequestBuilders.get("/" + READER))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("readingList"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("books"))
                .andExpect(MockMvcResultMatchers.model().attribute("books", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.model().attribute("books", Matchers.contains(Matchers.samePropertyValuesAs(bookEntity))));
    }

}
