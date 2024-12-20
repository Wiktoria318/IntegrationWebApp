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
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import pl.polsl.mostowska.integrationwebapp.model.*;

/**
 *
 * @author Wiktoria
 */
@WebServlet(name = "HistoryServlet", urlPatterns = {"/HistoryServlet"})
public class HistoryServlet extends HttpServlet {

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
        resultEntity.setResult(request.getParameter("result"));
        persistObject(resultEntity);
        List<String> history = (List<String>) getServletContext().getAttribute("history");
        try (PrintWriter out = response.getWriter()) {
            // Retrieve the last result from the cookie
            Cookie[] cookies = request.getCookies();
            String lastResult = "No previous result available.";
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("lastResult".equals(cookie.getName())) {
                        lastResult = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                    }
                }
            }

            // Display the history to the user
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Calculation History</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Calculation History at " + request.getContextPath() + "</h1>");
            out.println("<p>Last Result: " + lastResult + "</p>");
            if (history.isEmpty()) {
                out.println("<p>No history available.</p>");
            } else {
                out.println("<ul>");
                for (String record : history) {
                    out.println("<li>" + record + "</li>");
                }
                out.println("</ul>");
            }
            
            for(ResultsEntity resultFromDB: findObjects()) { out.println("Found object: " + resultFromDB.getResult() + "<BR>"); }
            out.println("<a href='index.html'>Back</a>");
            out.println("</body>");
            out.println("</html>");
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
        return "Short description";
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

    public List<ResultsEntity> findObjects() {
        List<ResultsEntity> resultList = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pl.polsl.lab_WebJPADemo_war_1.0PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            Query query = em.createQuery("SELECT r FROM ResultsEntity r");
            resultList = query.getResultList();
        } catch (PersistenceException e) {
            e.printStackTrace(); // replace with proper message for the client
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return resultList;
    }
}
