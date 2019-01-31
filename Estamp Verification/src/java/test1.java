/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author skysarthak
 */
public class test1 extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            try
		{

				String sig_hash;
                
                    		Process prc = Runtime.getRuntime().exec("./ret_sighash.sh");
                    
                    		BufferedReader stdInput = new BufferedReader(new
                    		InputStreamReader(prc.getInputStream()));
                    
                    		BufferedReader stdError = new BufferedReader(new
		            	InputStreamReader(prc.getErrorStream()));
		            
		            	// read the output from the command
		            	while ((sig_hash = stdInput.readLine()) != null)
		            	{
		                	out.println("Printing Hash of Signature Block: "+ sig_hash);
					File hFile = new File("/home/skysarthak/NetBeansProjects/estampsearch/sig_block.txt");
		            		FileOutputStream fos = new FileOutputStream(hFile);
		            		fos.write(sig_hash.getBytes());
		            		fos.flush();
		            	}
		            
		            	prc = Runtime.getRuntime().exec("./ret_sig.sh");
		            
		            	stdInput = new BufferedReader(new
		            	InputStreamReader(prc.getInputStream()));
		            
		            	stdError = new BufferedReader(new
		            	InputStreamReader(prc.getErrorStream()));
		            
		            	// read the output from the command
		            	String signature;
		            	while ((signature = stdInput.readLine()) != null)
		            	{
		                	out.println("Printing Signature of File from IPFS: " + signature);
		            	}
		            
		            	prc = Runtime.getRuntime().exec("python3 /home/skysarthak/NetBeansProjects/estampsearch/verify.py");
		            
		            	stdInput = new BufferedReader(new
		            	InputStreamReader(prc.getInputStream()));
		            
		            	stdError = new BufferedReader(new
		            	InputStreamReader(prc.getErrorStream()));
		            
		            	// read the output from the command
		            	String veri;
		            	
				while ((veri = stdInput.readLine()) != null)
		            	{
		                	out.println("Priting Verification Status: " + veri);
					File hFile = new File("/home/skysarthak/NetBeansProjects/estampsearch/ver.txt");
		        		FileOutputStream fos = new FileOutputStream(hFile);
		        		fos.write(veri.getBytes());
		        		fos.flush();
		            	}
		}
		catch(IOException e)
		{}
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

}
