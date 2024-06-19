package api.documentation.combine.member.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(value = {SpringExtension.class, RestDocumentationExtension.class})
class MemberControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void getMembers() throws Exception {
        ResultActions perform = mockMvc.perform(get("/members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").exists());

        perform.andDo(MockMvcRestDocumentation.document(
                "{class-name}/{method-name}",
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                responseFields(
                        fieldWithPath("[].id").description("아이디"),
                        fieldWithPath("[].name").description("이름"),
                        fieldWithPath("[].age").description("나이")
                )
        ));
    }

    @Test
    void getMemberById() throws Exception {
        String id = "John";

        // https://docs.spring.io/spring-restdocs/docs/current/reference/htmlsingle/#documenting-your-api-path-parameters
        // If you use MockMvc, to make the path parameters available for documentation,
        // you must build the request by using one of the methods on RestDocumentationRequestBuilders rather than MockMvcRequestBuilders.

        ResultActions perform = mockMvc.perform(RestDocumentationRequestBuilders.get("/members/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("John"))
                .andExpect(jsonPath("$.name").value("존"))
                .andExpect(jsonPath("$.age").value(30));

        perform.andDo(MockMvcRestDocumentation.document(
                "{class-name}/{method-name}",
                RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("id").description("아이디")
                ),
                responseFields(
                        fieldWithPath("id").description("아이디"),
                        fieldWithPath("name").description("이름"),
                        fieldWithPath("age").description("나이")
                )
        ));
    }
}