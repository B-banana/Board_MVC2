package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.BoardDetailAction;
import action.BoardWriteProAction;
import vo.ActionForward;

@WebServlet("*.bo")
public class BoardFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		ActionForward forward = null;
		Action action = null;
		
		if (command.equals("/boardWriterForm.bo")) {
			forward = new ActionForward();
			forward.setPath("/board/qna_board_write.jsp");
			
		} else if(command.equals("/boardWritePro.bo")) {
			action = new BoardWriteProAction();
			try {
				forward = action.execute(request, response);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else if(command.equals("/boardList.bo")) {
			action = new BoardDetailAction();
			try {
				forward = action.execute(request, response);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
		
	}

}
