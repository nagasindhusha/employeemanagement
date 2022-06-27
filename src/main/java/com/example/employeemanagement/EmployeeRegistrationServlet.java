package com.example.employeemanagement;


import com.example.employeemanagement.dao.EmployeeDAO;
import com.example.employeemanagement.pojo.Employee;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class EmployeeRegistrationServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private EmployeeDAO employeeDAO;

  public void init() {
    employeeDAO = new EmployeeDAO();
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    int registerEmployeeId = 0;

    String firstName = request.getParameter("firstName");
    String lastName = request.getParameter("lastName");
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String address = request.getParameter("address");
    String contact = request.getParameter("contact");

    Employee employee = new Employee();
    employee.setFirstName(firstName);
    employee.setLastName(lastName);
    employee.setUsername(username);
    employee.setPassword(password);
    employee.setContact(contact);
    employee.setAddress(address);

    try {
      registerEmployeeId = employeeDAO.registerEmployee(employee);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (registerEmployeeId > 0) {
      request.setAttribute("id", registerEmployeeId);
      request.setAttribute("firstName", employee.getFirstName());
      request.setAttribute("lastName", employee.getLastName());
      request.setAttribute("username", employee.getUsername());
      request.setAttribute("contact", employee.getContact());
      request.setAttribute("address", employee.getAddress());
      RequestDispatcher requestDispatcher = request.getRequestDispatcher("registrationSuccess.jsp");
      requestDispatcher.forward(request, response);
    }
  }
}