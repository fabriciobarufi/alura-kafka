package br.com.alura.ecommerce;

import br.com.alura.ecommerce.dispatcher.KafkaDispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderServlet extends HttpServlet {

    private final KafkaDispatcher orderDispatcher = new KafkaDispatcher<Order>();

    @Override
    public void destroy() {
        orderDispatcher.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            var orderId = req.getParameter("uuid");

            var email = req.getParameter("email");
            var amount = new BigDecimal(req.getParameter("amount"));

            var order = new Order(orderId, amount, email);

            var database = new OrdersDatabase();

            if (database.saveNew(order)) {

                orderDispatcher.send("ECOMMERCE_NEW_ORDER", email, new CorrelationId(NewOrderServlet.class.getSimpleName()), order);

                System.out.println("New Order sent succesfully!");
                resp.getWriter().println("New Order sent succesfully!");
                resp.setStatus(HttpServletResponse.SC_OK);

            } else {
                System.out.println("Old Order Received!");
                resp.getWriter().println("Old Order Received!");
                resp.setStatus(HttpServletResponse.SC_OK);
            }

        } catch (InterruptedException | SQLException | ExecutionException e) {
            throw new ServletException(e);
        }
    }
}
