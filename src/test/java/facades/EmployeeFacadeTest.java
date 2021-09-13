package facades;

import entities.Employee;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;
import entities.RenameMe;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class EmployeeFacadeTest {

    private static EntityManagerFactory emf;
    private static EmployeeFacade facade;

    public EmployeeFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = EmployeeFacade.getEmployeeFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Employee.deleteAllRows").executeUpdate();
            em.persist(new Employee("Mathias","Ourøgade",10000));
            em.persist(new Employee("Ellen", "Ourøgade",100));
            em.persist(new Employee("Lars", "Fiktivgade",50000));

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    void getEmployeeFacadeTest() {
    }

    @Test
    void createTest() {
        Employee employee = facade.create(new Employee("Jens","Aarhusgade",300));
        assertEquals("Jens",employee.getName());
    }

    @Test
    @Disabled //Reset id somehow?
    void getEmployeeById() {
        Employee employee = facade.getEmployeeById(1);
        assertEquals("Ellen",employee.getName());
    }

    @Test
    void getEmployeesByName() {
        List<Employee> employees = facade.getEmployeesByName("Mathias");
        assertEquals("Mathias",employees.get(0).getName());
    }

    @Test
    void getEmployeesWithHighestSalary() {
        List<Employee> employees = facade.getEmployeesWithHighestSalary();
        assertEquals("Lars",employees.get(0).getName());
    }

    @Test
    void getAllEmployeesTest() {
        assertEquals(3,facade.getAll().size());
    }

}
