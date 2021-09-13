package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.EmployeeDTO;
import entities.Employee;
import facades.Populator;
import utils.EMF_Creator;
import facades.EmployeeFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

//Todo Remove or change relevant parts before ACTUAL use
@Path("employee")
public class EmployeeResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final EmployeeFacade FACADE =  EmployeeFacade.getEmployeeFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
       
        long count = FACADE.getRenameMeCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":"+count+"}";  //Done manually so no need for a DTO
    }

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllEmployees() {
        Populator.populate();
        List<Employee> employees = FACADE.getAll();
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (Employee employee: employees) {
            EmployeeDTO employeeDTO = new EmployeeDTO(employee);
            employeeDTOS.add(employeeDTO);
        }

        //System.out.println("--------------->"+count);
        return new Gson().toJson(employeeDTOS);  //Done manually so no need for a DTO
    }

    @Path("id={id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getEmployeeById(@PathParam("id")Integer id) {
        Populator.populate();
        Employee employee = FACADE.getEmployeeById(id);
        EmployeeDTO employeeDTO = new EmployeeDTO(employee);
        return new Gson().toJson(employeeDTO);
    }

    @Path("highestPaid")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getHighestPaid() {
        Populator.populate();
        List<Employee> employees = FACADE.getEmployeesWithHighestSalary();

        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (Employee employee: employees) {
            EmployeeDTO employeeDTO = new EmployeeDTO(employee);
            employeeDTOS.add(employeeDTO);
        }

        //System.out.println("--------------->"+count);
        return new Gson().toJson(employeeDTOS);  //Done manually so no need for a DTO
    }

    @Path("/name/{name}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getEmployeeByName(@PathParam("name")String name) {
        Populator.populate();
        List<Employee> employees = FACADE.getEmployeesByName(name);
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (Employee employee: employees) {
            EmployeeDTO employeeDTO = new EmployeeDTO(employee);
            employeeDTOS.add(employeeDTO);
        }
        return new Gson().toJson(employeeDTOS);
    }


}
