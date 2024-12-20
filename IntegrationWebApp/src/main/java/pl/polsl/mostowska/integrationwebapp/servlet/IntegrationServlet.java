package pl.polsl.mostowska.integrationwebapp.servlet;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import pl.polsl.mostowska.integrationwebapp.model.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Wiktoria Mostowska
 */
@WebServlet(name = "IntegrationServlet", urlPatterns = {"/IntegrationServlet"})
public class IntegrationServlet extends HttpServlet {
    
    @Override
    public void init() throws ServletException {
        super.init();
        
        if (getServletContext().getAttribute("history") == null) {
            getServletContext().setAttribute("history", new ArrayList<String>());
        }
        
        getServletContext().setAttribute("integrationModel", new Rectangle());
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ResultsEntity resultEntity = new ResultsEntity();
        List<String> history = (List<String>) getServletContext().getAttribute("history");
        try (PrintWriter out = response.getWriter()) {
            IntegrationModel model = (IntegrationModel) getServletContext().getAttribute("integrationModel");
            String method = request.getParameter("method");
            String functionName = request.getParameter("function");
            double lowerBound = Double.parseDouble(request.getParameter("lowerBound"));
            double upperBound = Double.parseDouble(request.getParameter("upperBound"));
            double b = Double.parseDouble(request.getParameter("b"));
            double c = Double.parseDouble(request.getParameter("c"));
            int partitions = Integer.parseInt(request.getParameter("partitions"));
            
            // Parameter 'a' is only needed for QuadraticFunction
            double a = 0; // Default value for linear function

            // If function is quadratic, retrieve 'a' parameter
            if (functionName.equals("quadratic")) {
                a = Double.parseDouble(request.getParameter("a"));
            }
            
            FunctionModel function = switch (functionName) {
                case "linear" -> new LinearFunction(b, c);
                case "quadratic" -> new QuadraticFunction(a, b, c);
                default -> null;
            };
            
            if ("trapezoid".equals(method) && !(model instanceof Trapezoid)) {
                model = new Trapezoid();
                getServletContext().setAttribute("integrationModel", model);
            } else if ("rectangle".equals(method) && !(model instanceof Rectangle)) {
                model = new Rectangle();
                getServletContext().setAttribute("integrationModel", model);
            }
            
//            IntegrationModel integrationModel = switch (method) {
//                case "trapezoid" -> new Trapezoid();
//                case "rectangle" -> new Rectangle();
//                default -> null;
//            };

            if (function != null && model != null) {
                model.setFunction(function);
                IntegrationParameters parameters = new IntegrationParameters(lowerBound, upperBound, partitions);
                model.setParameters(parameters);
                double result = model.calculate();
//                out.println("<!DOCTYPE html>");
//                out.println("<html>");
//                out.println("<head>");
//                out.println("<title>Integration Result</title>");
//                out.println("</head>");
//                out.println("<body>");
//                out.println("<h1>Integration Result at " + request.getContextPath() + "</h1>");
//                out.println("<p>Method: " + method + "</p>");
//                out.println("<p>Function: " + functionName + " f(x) = " + (function instanceof QuadraticFunction ? a + "x² + " : "") + b + "x + " + c + "</p>");
//                out.println("<p>Lower Bound: " + lowerBound + "</p>");
//                out.println("<p>Upper Bound: " + upperBound + "</p>");
//                out.println("<p>Partitions: " + partitions + "</p>");
//                out.println("<p><strong>Result: " + result + "</strong></p>");
//                out.println("<a href=\"index.html\">Back to Home</a>");
//                out.println("</body>");
//                out.println("</html>");

                String record = "Method: " + method + ", Result: " + result +
                        " for bounds [" + lowerBound + ", " + upperBound + "] and " + partitions + " partitions, " +
                        "for function: " + functionName + " f(x) = " + (function instanceof QuadraticFunction ? a + "x² + " : "") + b + "x + " + c;
                
                resultEntity.setResult(record);
                persistObject(resultEntity);
                
                String encodedRecord = URLEncoder.encode(record, StandardCharsets.UTF_8);
                Cookie resultCookie = new Cookie("lastResult", encodedRecord);
                resultCookie.setMaxAge(24 * 60 * 60);
                response.addCookie(resultCookie);
                history.add(record);
                
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Integration Result</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Integration Result at " + request.getContextPath() + "</h1>");
                out.println("<h1>Calculation Result</h1>");
                out.println("<p>" + record + "</p>");
                out.println("<a href='index.html'>Back</a>");
                out.println("</body>");
                out.println("</html>");
            }
            else {out.println("<h1>Error: Invalid method or function selected.</h1>");}
        } catch (NumberFormatException e) {
            // Handle invalid number format
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input data format.");
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input data format.");
        } catch (Exception e) {
            // Handle other exceptions
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred during the integration.");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet for performing numerical integration calculations";
    }// </editor-fold>

    void persistObject(Object object) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pl.polsl.lab_WebJPADemo_war_1.0PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace(); // replace with proper message for the client
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
//    public List<ResultsEntity> findObjects() {
//        List<ResultsEntity> personList = null;
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pl.polsl.lab_WebJPADemo_war_1.0PU");
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//        try {
//            Query query = em.createQuery("SELECT p FROM Person p");
//            personList = query.getResultList();
//        } catch (PersistenceException e) {
//            e.printStackTrace(); // replace with proper message for the client
//            em.getTransaction().rollback();
//        } finally {
//            em.close();
//        }
//        return personList;
//    }
}
