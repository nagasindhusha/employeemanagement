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

@WebServlet("/search")
public class EmployeeSearchServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private EmployeeDAO employeeDAO;

  public void init() {
    employeeDAO = new EmployeeDAO();
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    Integer id = Integer.parseInt(request.getParameter("id"));
    try {
      Employee employee = employeeDAO.getEmployee(id);
      request.setAttribute("firstName", employee.getFirstName());
      request.setAttribute("lastName", employee.getLastName());
      request.setAttribute("username", employee.getUsername());
      request.setAttribute("contact", employee.getContact());
      request.setAttribute("address", employee.getAddress());
      RequestDispatcher requestDispatcher = request.getRequestDispatcher("employeeDetails.jsp");
      requestDispatcher.forward(request, response);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}