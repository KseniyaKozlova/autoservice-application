<h1>Autodiagnostic application</h1>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   <html>
        <body>
            <table border="1">
                <caption><h2>Processed transport</h2></caption>
                <tr>
                  <th>Type</th>
                  <th>Model</th>
                  <th>Cost</th>
                </tr>
                    <c:forEach var="transport" items="${listTransport}">
                        <tr>
                        <td><c:out  value="${transport.category.title}"/></td>
                        <td><c:out value="${transport.model}" /></td>
                           <td><c:out  value="${transport.category.cost}"/></td>
                        </tr>
                    </c:forEach>
            </table>

            <table border="1">
                 <caption><h2>Invalid transport</h2></caption>
                 <tr>
                    <th>Type</th>
                    <th>Model</th>
                 </tr>
                        <c:forEach var="transport" items="${invalidList}">
                            <tr>
                                <td><c:out  value="${transport.category.title}"/></td>
                                <td><c:out value="${transport.model}" /></td>
                            </tr>
                        </c:forEach>
                    </table>
        </body>
   </html>