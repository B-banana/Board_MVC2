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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doProcess(request, response);
	}
	

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		ActionForward forward = null;
		/*
	     * 서블릿에서 클라이언트로부터 요청을 전달받아 처리한 후
	     * 지정한 View 페이지로 포워딩할 때
	     * 포워딩 할 View 페이지의 주소(URL)와 포워딩 방식(Redirect  or  Dispatch) 을 
	     * 공통으로 다루기 위한 클래스
	     */
		Action action = null;
		System.out.println("디버깅 0: "+command);
		
		if (command.equals("/boardWriteForm.bo")) { //boardWriteForm.bo
			System.out.println("디버깅 입니다.");
			forward = new ActionForward();
			forward.setPath("/board/qna_board_write.jsp");
	
			System.out.println("디버깅 1: "+command);
			
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
				System.out.println("디버깅 2: "+forward.getPath().toString());
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
				System.out.println("디버깅 3: "+forward.getPath().toString());
			}
		}
		
	}

}
