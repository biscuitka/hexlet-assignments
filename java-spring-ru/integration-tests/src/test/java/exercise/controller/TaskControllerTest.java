package exercise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.model.Task;
import exercise.repository.TaskRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// BEGIN
public class TaskControllerTest {

    // END
    @SpringBootTest
    @AutoConfigureMockMvc
    static class ApplicationTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private Faker faker;

        @Autowired
        private ObjectMapper om;

        @Autowired
        private TaskRepository taskRepository;

        public Task setTestData() {
            Task task = new Task();
            task.setTitle(faker.lorem().word());
            task.setDescription(faker.lorem().paragraph());
            return task;
        }

        @AfterEach
        public void deleteAll() {
            taskRepository.deleteAll();
        }

        @Test
        public void testWelcomePage() throws Exception {
            var result = mockMvc.perform(get("/"))
                    .andExpect(status().isOk())
                    .andReturn();

            var body = result.getResponse().getContentAsString();
            assertThat(body).contains("Welcome to Spring!");
        }

        @Test
        public void testIndex() throws Exception {
            var result = mockMvc.perform(get("/tasks"))
                    .andExpect(status().isOk())
                    .andReturn();

            var body = result.getResponse().getContentAsString();
            assertThatJson(body).isArray();
        }

        @Test
        void show() throws Exception {
            Task task = taskRepository.save(setTestData());

            var result = mockMvc.perform(get("/tasks/" + task.getId()))
                    .andExpect(status().isOk())
                    .andReturn();
            Task foundTask = om.readValue(result.getResponse().getContentAsString(), Task.class);

            assertThat(foundTask.getTitle()).isEqualTo(task.getTitle());
            assertThat(foundTask.getDescription()).isEqualTo(task.getDescription());
        }

        @Test
        void create() throws Exception {
            Task task = setTestData();

            var result = mockMvc.perform(post("/tasks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(om.writeValueAsString(task)))
                    .andExpect(status().isCreated())
                    .andReturn();

            Task createdTask = om.readValue(result.getResponse().getContentAsString(), Task.class);

            assertThat(createdTask.getTitle()).isEqualTo(task.getTitle());
            assertThat(createdTask.getDescription()).isEqualTo(task.getDescription());
        }

        @Test
        void update() throws Exception {
            Task task = setTestData();
            long id = taskRepository.save(task).getId();
            Task updateRequest = setTestData();

            var result = mockMvc.perform(put("/tasks/" + id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(om.writeValueAsString(updateRequest)))
                    .andExpect(status().isOk())
                    .andReturn();

            Task updatedTask = om.readValue(result.getResponse().getContentAsString(), Task.class);

            assertThat(updatedTask.getTitle()).isEqualTo(updateRequest.getTitle());
            assertThat(updatedTask.getDescription()).isEqualTo(updateRequest.getDescription());
        }

        @Test
        void deleteById() throws Exception {
            Task task = setTestData();
            long id = taskRepository.save(task).getId();

            mockMvc.perform(delete("/tasks/" + id))
                    .andExpect(status().isOk())
                    .andReturn();
        }

        // BEGIN
    }
    // END
}
