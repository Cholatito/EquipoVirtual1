package pe.edu.upc.equipovirtual1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.equipovirtual1.dtos.CulturalEventBudgetDTO;
import pe.edu.upc.equipovirtual1.entities.CulturalActivity;
import pe.edu.upc.equipovirtual1.entities.CulturalCategory;
import pe.edu.upc.equipovirtual1.entities.CulturalEvent;
import pe.edu.upc.equipovirtual1.repositories.CulturalCategoryRepository;
import pe.edu.upc.equipovirtual1.repositories.IEventRepository;
import pe.edu.upc.equipovirtual1.servicesInterfaces.IActivityService;
import pe.edu.upc.equipovirtual1.servicesInterfaces.ICategoryService;
import pe.edu.upc.equipovirtual1.servicesInterfaces.IEventService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class EquipoVirtual1ApplicationTests {
    private static final String CATEGORY_JSON = """
            {"name":"Music","description":"Concerts","type":"Art",
             "targetAudience":"Everyone","active":false}
            """;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CulturalCategoryRepository categoryRepository;
    @Autowired
    private IEventRepository eventRepository;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IEventService eventService;
    @Autowired
    private IActivityService activityService;
    @Autowired
    private EntityManager entityManager;

    @Test
    void contextLoads() {
        assertThat(categoryService).isNotNull();
        assertThat(eventService).isNotNull();
        assertThat(activityService).isNotNull();
    }

    @Test
    void hub01RegistersCategoryUsingDtoAndReturns201() throws Exception {
        mockMvc.perform(post("/api/events/news")
                        .contentType(MediaType.APPLICATION_JSON).content(CATEGORY_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Music"))
                .andExpect(jsonPath("$.description").value("Concerts"))
                .andExpect(jsonPath("$.type").value("Art"))
                .andExpect(jsonPath("$.targetAudience").value("Everyone"))
                .andExpect(jsonPath("$.active").value(false));

        entityManager.flush();
        assertThat(categoryRepository.findAll()).singleElement().satisfies(category -> {
            assertThat(category.getId()).isPositive();
            assertThat(category.getName()).isEqualTo("Music");
            assertThat(category.getDescription()).isEqualTo("Concerts");
            assertThat(category.getType()).isEqualTo("Art");
            assertThat(category.getTargetAudience()).isEqualTo("Everyone");
            assertThat(category.isActive()).isFalse();
        });
    }

    static Stream<Arguments> invalidCategories() {
        Stream<Arguments> textFields = Stream.of("name", "description", "type", "targetAudience")
                .flatMap(field -> Stream.of(
                        Arguments.of(field + " missing", CATEGORY_JSON.replaceAll(
                                "\\\"" + field + "\\\":\\\"[^\\\"]*\\\",", "")),
                        Arguments.of(field + " null", CATEGORY_JSON.replaceAll(
                                "\\\"" + field + "\\\":\\\"[^\\\"]*\\\"", "\"" + field + "\":null")),
                        Arguments.of(field + " blank", CATEGORY_JSON.replaceAll(
                                "\\\"" + field + "\\\":\\\"[^\\\"]*\\\"", "\"" + field + "\":\"   \""))));
        return Stream.concat(textFields, Stream.of(
                Arguments.of("active missing", CATEGORY_JSON.replace(",\"active\":false", "")),
                Arguments.of("active null", CATEGORY_JSON.replace("\"active\":false", "\"active\":null"))));
    }

    @ParameterizedTest(name = "HUB01 rejects {0}")
    @MethodSource("invalidCategories")
    void hub01RejectsMissingNullOrBlankFields(String scenario, String body) throws Exception {
        mockMvc.perform(post("/api/events/news")
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest());
        assertThat(categoryRepository.count()).isZero();
    }

    @Test
    void hub02Returns200AndEmptyListWhenThereAreNoActivities() throws Exception {
        mockMvc.perform(get("/api/events/cultural"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void hub02SumsQuantityTimesCostPerEventAndCategoryWithoutMergingEqualNames() throws Exception {
        CulturalEvent firstEvent = eventRepository.save(event("Festival"));
        CulturalEvent secondEvent = eventRepository.save(event("Festival"));
        CulturalCategory music = categoryRepository.save(category("Music"));
        CulturalCategory dance = categoryRepository.save(category("Dance"));
        CulturalCategory otherMusic = categoryRepository.save(category("Music"));

        activityService.save(activity(firstEvent, music, 2, "10.25"));
        activityService.save(activity(firstEvent, music, 3, "4.10"));
        activityService.save(activity(firstEvent, dance, 4, "2.50"));
        activityService.save(activity(secondEvent, music, 1, "7.00"));
        activityService.save(activity(firstEvent, otherMusic, 2, "3.00"));
        entityManager.flush();
        entityManager.clear();

        assertThat(eventService.e2_findCulturalEventBudgets())
                .extracting(CulturalEventBudgetDTO::getPresupuestoTotal)
                .usingElementComparator(BigDecimal::compareTo)
                .containsExactlyInAnyOrder(new BigDecimal("32.80"), new BigDecimal("10.00"),
                        new BigDecimal("7.00"), new BigDecimal("6.00"));

        mockMvc.perform(get("/api/events/cultural"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$[0].evento").value("Festival"))
                .andExpect(jsonPath("$[0].categoria").value("Dance"))
                .andExpect(jsonPath("$[0].presupuesto_total").value(10.00))
                .andExpect(jsonPath("$[1].categoria").value("Music"))
                .andExpect(jsonPath("$[1].presupuesto_total").value(32.80))
                .andExpect(jsonPath("$[2].presupuesto_total").value(6.00))
                .andExpect(jsonPath("$[3].presupuesto_total").value(7.00))
                .andExpect(jsonPath("$[0].eventName").doesNotExist())
                .andExpect(jsonPath("$[0].categoryName").doesNotExist())
                .andExpect(jsonPath("$[0].totalBudget").doesNotExist())
                .andExpect(jsonPath("$[0].presupuestoTotal").doesNotExist());
    }

    private CulturalEvent event(String name) {
        return new CulturalEvent(null, name, "Description", "Lima", "Onsite",
                LocalDate.of(2026, 9, 17), 100, "Active", new BigDecimal("99999.00"));
    }

    private CulturalCategory category(String name) {
        return new CulturalCategory(null, name, "Description", "Art", "Everyone", true);
    }

    private CulturalActivity activity(CulturalEvent event, CulturalCategory category,
                                      int quantity, String cost) {
        return new CulturalActivity(null, "Activity", "Description", quantity,
                LocalDate.of(2026, 9, 17), LocalTime.of(10, 0), 60, new BigDecimal(cost),
                "Organizer", event, category);
    }
}
