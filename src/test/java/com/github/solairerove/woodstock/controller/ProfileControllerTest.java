package com.github.solairerove.woodstock.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.solairerove.woodstock.Application;
import com.github.solairerove.woodstock.domain.Profile;
import com.github.solairerove.woodstock.dto.ProfileDTO;
import com.github.solairerove.woodstock.repository.ProfileRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static com.github.solairerove.woodstock.utils.EntityUtils.NUMBER_OF_ENTITIES_IN_COLLECTION;
import static com.github.solairerove.woodstock.utils.EntityUtils.generateProfile;
import static com.github.solairerove.woodstock.utils.EntityUtils.generateProfileCollection;
import static com.github.solairerove.woodstock.utils.EntityUtils.generateProfileDTO;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by krivitski-no on 9/28/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = Application.class)
@Transactional
public class ProfileControllerTest {

    private static final String API_PATH = "/api/profiles";
    private static final String COLLECTION_JSON_PATH = "_embedded.profileList";

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ProfileRepository repository;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        repository.deleteAll();
    }

    @After
    public void clear() {
        repository.deleteAll();
    }

    @Test
    public void getAllProfilesTest() throws Exception {
        repository.save(generateProfileCollection());

        mockMvc.perform(get(API_PATH)
                .accept(APPLICATION_JSON_UTF8)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$." + COLLECTION_JSON_PATH, hasSize(NUMBER_OF_ENTITIES_IN_COLLECTION)))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }

    @Test
    public void getProfileTest() throws Exception {
        Profile profile = generateProfile();
        repository.save(profile);
        Long id = profile.getId();

        mockMvc.perform(get(API_PATH + "/" + id)
                .accept(APPLICATION_JSON_UTF8)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }

    @Test
    public void createProfileTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ProfileDTO profileDTO = generateProfileDTO();

        mockMvc.perform(post(API_PATH)
                .accept(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(profileDTO))
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }

    @Test
    public void updateProfileTest() throws Exception {
        Profile profile = generateProfile();
        repository.save(profile);
        Long id = profile.getId();

        ObjectMapper objectMapper = new ObjectMapper();
        ProfileDTO profileDTO = generateProfileDTO();

        mockMvc.perform(put(API_PATH + "/" + id)
                .accept(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(profileDTO))
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }

    @Test
    public void deleteProfileTest() throws Exception {
        Profile profile = generateProfile();
        repository.save(profile);
        Long id = profile.getId();

        mockMvc.perform(delete(API_PATH + "/" + id)
                .accept(APPLICATION_JSON_UTF8)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", is(id.intValue())))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }

    @Test
    public void deleteAllTest() throws Exception {
        repository.save(generateProfileCollection());

        mockMvc.perform(delete(API_PATH + "/" + "delete_all")
                .accept(APPLICATION_JSON_UTF8)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isAccepted())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(NUMBER_OF_ENTITIES_IN_COLLECTION)))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }
}
