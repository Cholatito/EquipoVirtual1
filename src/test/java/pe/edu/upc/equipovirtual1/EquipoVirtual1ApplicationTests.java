package pe.edu.upc.equipovirtual1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class EquipoVirtual1ApplicationTests {
    @Autowired MockMvc mvc;
    @Autowired JdbcTemplate jdbc;

    @Test
    void registersCategoryAndAcceptsFalseAsRequiredBoolean() throws Exception {
        mvc.perform(post("/api/events/news").contentType("application/json").content("""
                {"name":"Teatro","description":"Artes escénicas","type":"Arte",
                 "targetAudience":"Público general","active":false}
                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNumber())
                .andExpect(jsonPath("name").value("Teatro"))
                .andExpect(jsonPath("active").value(false));
    }

    @Test
    void rejectsMissingFieldsAndMalformedJson() throws Exception {
        mvc.perform(post("/api/events/news").contentType("application/json").content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors.name").exists())
                .andExpect(jsonPath("errors.description").exists())
                .andExpect(jsonPath("errors.type").exists())
                .andExpect(jsonPath("errors.targetAudience").exists())
                .andExpect(jsonPath("errors.active").exists());
        mvc.perform(post("/api/events/news").contentType("application/json").content("{"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectsBlankAndOverlongNames() throws Exception {
        for (String invalidName : new String[]{"   ", "x".repeat(101)}) {
            mvc.perform(post("/api/events/news").contentType("application/json").content("""
                    {"name":"%s","description":"Descripción","type":"Arte",
                     "targetAudience":"General","active":true}
                    """.formatted(invalidName)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("errors.name").exists());
        }
    }

    @Test
    void nativeQuerySumsActivitiesAndSeparatesEventsWithSameName() throws Exception {
        jdbc.update("INSERT INTO cultural_event (id,name,description,location,modality,event_date,capacity,status,budget) VALUES (101,'Festival','Descripción','Lima','Presencial','2026-10-01',100,'Activo',1000),(102,'Festival','Descripción','Lima','Presencial','2026-10-02',100,'Activo',1000)");
        jdbc.update("INSERT INTO cultural_category (id,name,description,type,target_audience,active) VALUES (101,'Teatro','Descripción','Arte','General',true),(102,'Música','Descripción','Arte','General',true)");
        jdbc.update("INSERT INTO cultural_activity (name,description,quantity,activity_date,start_time,duration,cost,responsible,event_id,category_id) VALUES ('A','Descripción',2,'2026-10-01','10:00:00',60,10.50,'Ana',101,101),('B','Descripción',3,'2026-10-01','11:00:00',60,5.00,'Ana',101,101),('C','Descripción',1,'2026-10-01','12:00:00',60,7.00,'Ana',101,102),('D','Descripción',4,'2026-10-02','10:00:00',60,2.00,'Ana',102,101)");
        mvc.perform(get("/api/events/cultural"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].eventName").value("Festival"))
                .andExpect(jsonPath("$[0].categoryName").value("Teatro"))
                .andExpect(jsonPath("$[0].totalBudget").value(36.0))
                .andExpect(jsonPath("$[1].totalBudget").value(7.0))
                .andExpect(jsonPath("$[2].totalBudget").value(8.0));
    }

    @Test
    void exposesOpenApiAndSwaggerUi() throws Exception {
        mvc.perform(get("/v3/api-docs")).andExpect(status().isOk())
                .andExpect(jsonPath("paths['/api/events/news']").exists())
                .andExpect(jsonPath("paths['/api/events/cultural']").exists());
        mvc.perform(get("/swagger-ui/index.html")).andExpect(status().isOk());
    }

    @Test
    void contextLoads() {
    }

}
