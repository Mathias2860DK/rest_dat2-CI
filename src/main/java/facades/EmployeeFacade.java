package facades;

import entities.Employee;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import utils.EMF_Creator;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class EmployeeFacade {

    private static EmployeeFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private EmployeeFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static EmployeeFacade getEmployeeFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new EmployeeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public Employee create(Employee employee){
        //Employee employee = new Employee("testName","testAdress",999);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(employee);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return employee;
    }
    public Employee getEmployeeById(Integer id){
        EntityManager em = emf.createEntityManager();
        return em.find(Employee.class,id);
    }

    public List<Employee> getEmployeesByName(String name){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Employee> query = (TypedQuery<Employee>) em.createQuery("SELECT e FROM Employee e WHERE e.name = :name");
        query.setParameter("name",name);
        return query.getResultList();
    }
    
    //TODO Remove/Change this before use
    public long getRenameMeCount(){
        EntityManager em = emf.createEntityManager();
        try{
            long renameMeCount = (long)em.createQuery("SELECT COUNT(r) FROM RenameMe r").getSingleResult();
            return renameMeCount;
        }finally{  
            em.close();
        }
    }

    public List<Employee> getEmployeesWithHighestSalary(){
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT e FROM Employee e WHERE e.salary = (SELECT MAX(e2.salary) FROM Employee e2)");
        return query.getResultList();
    }
    
    public List<Employee> getAll(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e", Employee.class);
        List<Employee> employees = query.getResultList();
        return employees;
    }
    
    public static void main(String[] args) {
        Populator.populate(); //Data to test the methods.

        emf = EMF_Creator.createEntityManagerFactory();
        EmployeeFacade fe = getEmployeeFacade(emf);
        System.out.println("#### getEmployeeById ####");
        System.out.println(fe.getEmployeeById(1));
        System.out.println("#### getEmployeesByName ####");
        System.out.println(fe.getEmployeesByName("testName"));//Gets all Employees with the specifed name
        System.out.println("#### getAllEmployees ####");
        fe.getAll().forEach(dto->System.out.println(dto));
        System.out.println("#### getEmployeesWithHighestSalary ####");
        System.out.println(fe.getEmployeesWithHighestSalary());
        System.out.println("#### createEmployee ####");
        Employee employee = fe.create(new Employee("madeInEmpFacade","emdfacade",22));
        System.out.println(employee);

    }

}
