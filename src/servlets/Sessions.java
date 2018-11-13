package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.SessionsLogic;
import tools.ServletUtils;

public class Sessions extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		SessionsLogic sessionslogic = new SessionsLogic(req);
		sessionslogic.connectUser();
        
        ServletUtils.answerToClient(resp, sessionslogic.toJSON());
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		SessionsLogic sessionslogic = new SessionsLogic(req);
		sessionslogic.invalidateSession();;
        
        ServletUtils.answerToClient(resp, sessionslogic.toJSON());
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        out.print("[cookie]<ul>");
        
        HttpSession session = req.getSession(false);
        
        if(session != null) {
        	Enumeration e = (Enumeration) (session.getAttributeNames());
			while ( e.hasMoreElements()) {
			    Object tring;
			    if((tring = e.nextElement())!=null) {
			        out.println("<li>"+ tring +" : "+ session.getAttribute((String) tring));
			    }
			}
	        out.println("</li><li>ID:"+session.getId());
	        Date createTime = new Date(session.getCreationTime());
	        out.println("</li><li>creation time:"+createTime.toString());
	        Date lastTime = new Date(session.getLastAccessedTime());
	        out.println("</li><li>last accessed time:"+lastTime.toString());
	        out.println("</li><li>max inactive interval:"+session.getMaxInactiveInterval());
	        out.println("</li><li>is new:"+session.isNew());
	        out.println("</li></ul>");
        }else {
        	out.println("null");
        }
        out.flush();   
	}
}
